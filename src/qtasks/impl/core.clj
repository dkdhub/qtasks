(ns qtasks.impl.core
  (:import [java.util UUID Properties]
           [org.quartz.spi JobFactory]
           [org.quartz SchedulerException]))

(defn uuid [] (str (UUID/randomUUID)))

(defn map->properties
  [m]
  (let [p (Properties.)]
    (doseq [[k v] m]
      (.setProperty p (name k) (str v)))
    p))

(defn make-job-factory
  [scheduler]
  (reify JobFactory
    (newJob [_ bundle _]
      (let [detail (.getJobDetail bundle)
            data-map (.getJobDataMap detail)
            class (.getJobClass detail)
            sym (symbol (get data-map "ns") (get data-map "name"))]
        (try
          (let [var (resolve sym)
                ctor (.getDeclaredConstructor class (into-array [Object Object]))]
            (.newInstance ctor (into-array Object [@var scheduler])))
          (catch Exception _
            (throw (SchedulerException. (format "Problem resolving symbol '%s'" sym)))))))))
