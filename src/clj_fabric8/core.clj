(ns clj-fabric8.core
  (:require [clojure.string :as str])
  (:import (io.fabric8.kubernetes.client DefaultKubernetesClient)
           (io.fabric8.kubernetes.client.dsl.base CustomResourceDefinitionContext$Builder)))

(defn default-client [] (DefaultKubernetesClient.))

(defmacro resource-call
  [client method type & opts]
  `(-> ~client
       (.customResource ~type)
       (~method ~@opts)))

; ref: https://javadoc.io/doc/io.fabric8/kubernetes-client/latest/io/fabric8/kubernetes/client/dsl/internal/RawCustomResourceOperationsImpl.html
(defmacro resource-list' [client type & opts] `(resource-call ~client .list ~type ~@opts))
(defmacro resource-list [client type & opts] `(get (resource-list' ~client ~type ~@opts) "items"))
(defmacro resource-get [client type & opts] `(resource-call ~client .get ~type ~@opts))
(defmacro resource-create! [client type & opts] `(resource-call ~client .create ~type ~@opts))
(defmacro resource-delete! [client type & opts] `(resource-call ~client .delete ~type ~@opts))
(defmacro resource-apply! [client type & opts] `(resource-call ~client .createOrReplace ~type ~@opts))
(defmacro resource-edit! [client type & opts] `(resource-call ~client .edit ~type ~@opts))

(defn defresource
  [name & {:keys [group version kind plural scope]
           :or {version "v1" scope "Namespaced"}}]
  (let [[plural' group'] (str/split name #"\." 2)
        plural (or plural plural')
        group (or group group')]
    (-> (new CustomResourceDefinitionContext$Builder)
        (.withScope scope)
        (.withGroup (if (empty? group) "../api" group))
        (.withVersion version)
        (.withName name)
        (.withKind kind)
        (.withPlural plural)
        .build)))
