(ns clj-fabric8.util)

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
