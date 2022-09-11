(defproject com.dkdhub/qtasks "0.5.1"
  :description "DKD Quartz Library for Clojure"
  :url "http://dkdhub.com"
  :license {:name "Proprietary"
            :url  "http://dkdhub.com/licenses/base.html"}
  :dependencies [[org.quartz-scheduler/quartz "2.3.2"]
                 [org.quartz-scheduler/quartz-jobs "2.3.2"]
                 [com.stuartsierra/component "1.1.0"]
                 [prismatic/plumbing "0.6.0"]
                 [org.clojure/core.async "1.5.648"]])

(cemerick.pomegranate.aether/register-wagon-factory!
  "scp" #(let [c (resolve 'org.apache.maven.wagon.providers.ssh.external.ScpExternalWagon)]
           (clojure.lang.Reflector/invokeConstructor c (into-array []))))
