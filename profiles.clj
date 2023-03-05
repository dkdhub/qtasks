{:provided {:omit-source true
            :aot               :all
            :java-source-paths ["java"]
            :javac-options     ["-source" "9" "-target" "9" "-g:none"]
            :jar-exclusions    [#"\.java"]}

 :dev      {:dependencies        [[org.clojure/clojure "1.11.1"]]
            :java-source-paths   ["java"]}}
