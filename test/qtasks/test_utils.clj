(ns qtasks.test-utils
  (:require [qtasks.core :as qtasks]
            [clojure.core.async :as async]))


(defn async-res
  ([ch] (async-res ch 5))
  ([ch seconds]
     (async/alt!!
       (async/timeout (* seconds 1000)) (throw (Exception. "Timeout"))
       ch ([v ch] v))))

(def ^:dynamic *scheduler*)

(defn with-scheduler
  [f]
  (binding [*scheduler* (-> (qtasks/make-scheduler {:threadPool.threadCount 1})
                            (qtasks/start))]
    (try
      (f)
      (finally
        (qtasks/stop *scheduler*)))))
