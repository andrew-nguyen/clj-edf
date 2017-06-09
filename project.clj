(defproject clj-edf "0.1.0-SNAPSHOT"
  :description "Clojure wrapper around EDF4J for working with European Data Format files"

  :url "https://github.com/andrew-nguyen/clj-edf"

  :license {:name "MIT License"
            :url  "https://opensource.org/licenses/MIT"}

  :prep-tasks ["javac" "compile"]

  :source-paths ["src/clj"]

  :java-source-paths ["src/java"]
  :javac-options ["-target" "1.8" "-source" "1.8"]

  :dependencies [[org.clojure/clojure "1.8.0"]

                 [clj-time "0.13.0"]]
  )
