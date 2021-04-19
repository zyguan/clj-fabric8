(defproject clj-fabric8 "0.1.0-SNAPSHOT"
  :description "A simple wrapper for fabric8"
  :url "https://github.com/zyguan/clj-fabric8"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.3" :scope "provided"]
                 [io.fabric8/kubernetes-client "5.2.1"]]
  :repl-options {:init-ns clj-fabric8.core})
