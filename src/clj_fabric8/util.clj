(ns clj-fabric8.util
  (:import (java.io ByteArrayOutputStream)
           (io.fabric8.kubernetes.client.dsl ExecListener)))

(defn pod-log
  ([client namespace name]
   (pod-log client namespace name ""))
  ([client namespace name container]
   (-> client
       (.pods)
       (.withName name)
       (.inNamespace namespace)
       (.inContainer container)
       (.getLogReader))))

(defn pod-file
  ([client namespace name path]
   (pod-file client namespace name "" path))
  ([client namespace name container path]
   (-> client
       (.pods)
       (.withName name)
       (.inNamespace namespace)
       (.inContainer container)
       (.file path)
       (.read))))

(defn pod-file-exists?
  ([client namespace name path]
   (pod-file-exists? client namespace name "" path))
  ([client namespace name container path]
   (let [out (ByteArrayOutputStream.)
         ok? (promise)]
     (try
       (with-open [w (-> client
                         (.pods)
                         (.withName name)
                         (.inNamespace namespace)
                         (.inContainer container)
                         (.writingOutput out)
                         (.usingListener (reify
                                           ExecListener
                                           (onOpen [this _])
                                           (onFailure [this _ _] (deliver ok? false))
                                           (onClose [this code _] (deliver ok? (= 1000 code)))))
                         (.exec (into-array ["sh" "-c" (str "test -f " path "; echo -n $?")])))]
         (and @ok? (= (str out) "0")))
       (catch Exception _ nil)))))

(defprotocol Clojurizable
  (clojurize [o]))

(extend-protocol Clojurizable
  java.util.Map
  (clojurize [o] (let [entries (.entrySet o)]
                   (reduce (fn [m [^String k v]]
                             (assoc m (keyword k) (clojurize v)))
                           {} entries)))
  java.util.List
  (clojurize [o] (vec (map clojurize o)))
  java.lang.Object
  (clojurize [o] o)
  nil
  (clojurize [_] nil))
