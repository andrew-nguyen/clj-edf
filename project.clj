(defproject clj-edf "0.1.0-SNAPSHOT"
  :description "FIXME: write description"

  :url "http://example.com/FIXME"

  :license {:name "MIT License"
            :url  "https://opensource.org/licenses/MIT"}

  :prep-tasks ["javac" "compile"]

  :source-paths ["src/clj"]

  :java-source-paths ["src/java"]
  :javac-options ["-target" "1.8" "-source" "1.8"]

  :dependencies [[org.clojure/clojure "1.8.0"]

                 [clj-time "0.13.0"]]
  )
