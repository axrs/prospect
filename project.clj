(defproject io.axrs/prospect "0.0.0-SNAPSHOT"
  :description "Simple Clojure Namespace function discovery tool for CLIs"
  :url "https://github.com/axrs/prospect"
  :license {:name         "Eclipse Public License - v 2.0"
            :url          "https://www.eclipse.org/legal/epl-v20.html"
            :distribution :repo}
  :min-lein-version "2.8.1"
  :dependencies []
  :clean-targets ["target"]
  :source-paths ["src"]
  :test-paths ["test"]
  :profiles {:dev {:dependencies [[org.clojure/clojure "1.10.1"]]}})
