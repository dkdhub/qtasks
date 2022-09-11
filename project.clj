(defproject com.dkdhub/qtasks "1.0.0"
  :description "DKD Quartz Library for Clojure"
  :url "http://dkdhub.com"
  :license {:name "Dual MIT & Proprietary"}
  :dependencies [[org.quartz-scheduler/quartz "2.3.2"]
                 [org.quartz-scheduler/quartz-jobs "2.3.2"]
                 [com.stuartsierra/component "1.1.0"]
                 [prismatic/plumbing "0.6.0"]
                 [org.clojure/core.async "1.5.648"]])
