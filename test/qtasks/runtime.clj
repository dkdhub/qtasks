(ns qtasks.runtime
  (:require [clojure.test :refer :all]
            [qtasks.core :as qtasks])
  (:import (java.util Date)))

(def job (fn [sch n] (println ">>> I'm a job" n "<<< @" (type sch))))
(deftest runtime-job
  (testing "Running periodical job"
    (let [*scheduler* (-> (qtasks/make-scheduler {:threadPool.threadCount 2
                                                  :threadPool.class       'qtasks.QtasksThreadPool
                                                  :jobStore.class         'org.quartz.simpl.RAMJobStore})
                          (qtasks/start))
          ident-1 "periodical-job-runtime-test-1"
          ident-2 "periodical-job-runtime-test-2"
          jid-1 (qtasks/schedule-job *scheduler* (var job) [1]
                                     :job {:identity ident-1}
                                     :trigger {:start-at (Date. ^Long (+ 1000 (System/currentTimeMillis)))})
          jid-2 (qtasks/schedule-job *scheduler* (var job) [2]
                                     :job {:identity ident-2}
                                     :trigger {:simple {:repeat           :inf
                                                        ;; msecs
                                                        :interval         1000
                                                        :misfire-handling :next-with-remaining-count}})]

      (is (true? (qtasks/check-job-exists *scheduler* ident-1)))
      (is (true? (qtasks/check-job-exists *scheduler* ident-2)))

      (Thread/sleep 5000)

      (qtasks/delete-job *scheduler* ident-1)
      (qtasks/delete-job *scheduler* ident-2)

      (is (false? (qtasks/check-job-exists *scheduler* ident-1)))
      (is (false? (qtasks/check-job-exists *scheduler* ident-2)))

      (qtasks/stop *scheduler*))))
