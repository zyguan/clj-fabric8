(ns clj-fabric8.apis.apps
  (:require [clj-fabric8.core :refer [defresource]]))

(def ControllerRevision (defresource "controllerrevisions.apps" :version "v1" :kind "ControllerRevision" :scope "Namespaced"))
(def DaemonSet (defresource "daemonsets.apps" :version "v1" :kind "DaemonSet" :scope "Namespaced"))
(def Deployment (defresource "deployments.apps" :version "v1" :kind "Deployment" :scope "Namespaced"))
(def ReplicaSet (defresource "replicasets.apps" :version "v1" :kind "ReplicaSet" :scope "Namespaced"))
(def StatefulSet (defresource "statefulsets.apps" :version "v1" :kind "StatefulSet" :scope "Namespaced"))
