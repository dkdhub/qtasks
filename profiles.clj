{:provided {:omit-source       true
            :aot               :all
            :dependencies      [[com.taoensso/timbre "5.2.1"]]
            :java-source-paths ["java"]
            :javac-options     ["-source" "9" "-target" "9" "-g:none"]
            :jar-exclusions    [#"\.java"]}
 :dev      {:dependencies        [[org.clojure/clojure "1.11.1"]
                                  [org.slf4j/slf4j-api "1.7.36"]
                                  [ch.qos.logback/logback-classic "1.2.11"]
                                  [org.postgresql/postgresql "42.4.1"]
                                  ;; Loggers
                                  ;; -------
                                  [com.taoensso/timbre "5.2.1"]
                                  [com.fzakaria/slf4j-timbre "0.3.21"]
                                  [org.slf4j/slf4j-api "1.7.36"]
                                  [org.slf4j/log4j-over-slf4j "1.7.36"]
                                  [org.slf4j/jul-to-slf4j "1.7.36"]
                                  [org.slf4j/jcl-over-slf4j "1.7.36"]]
            :java-source-paths   ["java"]
            :plugins             [[org.apache.maven.wagon/wagon-ssh-external "3.5.2"]
                                  [org.apache.maven.wagon/wagon-http-lightweight "3.5.2"]]
            :deploy-repositories [["private-jars-scp" {:url              "scp://local.repo/home/clojar/data/dev_repo/"
                                                       :username         "clojar"
                                                       :private-key-file :env/clojure_ssh_key}]]}}
