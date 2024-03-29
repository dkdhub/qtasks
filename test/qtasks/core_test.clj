(ns qtasks.core-test
  (:require [clojure.test :refer :all]
            [qtasks.core :as qtasks]
            [qtasks.test-utils :refer [async-res with-scheduler *scheduler*]]))

(qtasks/defjob simple-job
               [scheduler first-name last-name]
               nil)

(qtasks/defjob stateful-job
               [scheduler state]
               (str state "(.)"))

(qtasks/defjob check-listeners-job
               [scheduler]
               (.setResult
                 (:qtasks/execution-context scheduler)
                 (set (keys scheduler))))

(use-fixtures :each with-scheduler)

(deftest defjob-test
  (testing "Simple job"
    (let [listener (qtasks/add-listener *scheduler*
                                        {:key ["test-suite" "task-1"]} :to-be-executed)]
      (simple-job *scheduler* ["Petru" "Yanovicy"]
                  :job {:identity "task-1"
                        :group    "test-suite"})
      (let [res (async-res listener)
            data-map (-> (.getJobDetail res) (.getJobDataMap))]
        (is (= ["Petru" "Yanovicy"] (get data-map "arguments")))
        (is (= nil (get data-map "state"))))))

  (testing "Stateful job"
    (let [listener (qtasks/add-listener *scheduler*
                                        {:key ["test-suite" "task-2"]} :was-executed)]
      (stateful-job *scheduler* []
                    :job {:identity "task-2"
                          :group    "test-suite"
                          :state    "(.)(.)"})

      (let [res (async-res listener)
            data-map (-> (.getJobDetail res) (.getJobDataMap))]
        (is (= "(.)(.)(.)" (get data-map "state"))))))

  (testing "named jobs"
    (is (false? (qtasks/check-job-exists *scheduler* "task-3")))
    (simple-job *scheduler* ["Petru" "Yanovicy"]
                :job {:identity "task-3"}
                :trigger {:cron "*/10 * * * * ?"})
    (is (true? (qtasks/check-job-exists *scheduler* "task-3")))
    (qtasks/delete-job *scheduler* "task-3")
    (is (false? (qtasks/check-job-exists *scheduler* "task-3")))))

(deftest remove-listener-test
  (testing "Remove a listener"
    (println "T (remove-listener-test) <I> >>>>>" (-> *scheduler* :qtasks/listeners))
    (let [listener (qtasks/add-listener *scheduler*
                                        {:everything true} :to-be-executed)]
      (println "T (remove-listener-test) <II> >>>>" (-> *scheduler* :qtasks/listeners))
      (qtasks/remove-listener *scheduler* listener)
      (println "T (remove-listener-test) <III> >>>" (-> *scheduler* :qtasks/listeners))
      (is (empty? (-> *scheduler* :qtasks/listeners deref))))))

(deftest listeners-available
  (testing "Listeners are available inside job"

    (let [listener (qtasks/add-listener *scheduler*
                                        {:key ["test-suite" "task-listener"]} :was-executed)]
      (check-listeners-job *scheduler* []
                           :job {:identity "task-listener" :group "test-suite"})
      (let [res (async-res listener)
            job-result (.getResult res)]
        (is (:qtasks/listeners job-result))))))
