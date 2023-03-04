{:provided {:omit-source       true
            :aot               :all
            :dependencies      [[com.taoensso/timbre "6.1.0"]]
            :java-source-paths ["java"]
            :javac-options     ["-source" "9" "-target" "9" "-g:none"]
            :jar-exclusions    [#"\.java"]}
 :dev      {:dependencies        [[org.clojure/clojure "1.11.1"]
                                  [org.slf4j/slf4j-api "2.0.6"]
                                  [ch.qos.logback/logback-classic "1.4.5"]
                                  [org.postgresql/postgresql "42.5.4"]
                                  ;; Loggers
                                  ;; -------
                                  [com.taoensso/timbre "6.1.0"]
                                  [com.fzakaria/slf4j-timbre "0.3.21"]
                                  [org.slf4j/slf4j-api "2.0.6"]
                                  [org.slf4j/log4j-over-slf4j "2.0.6"]
                                  [org.slf4j/jul-to-slf4j "2.0.6"]
                                  [org.slf4j/jcl-over-slf4j "2.0.6"]]
            :java-source-paths   ["java"]
            :plugins             []
            :deploy-repositories []}}
