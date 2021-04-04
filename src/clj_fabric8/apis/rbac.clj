(ns clj-fabric8.apis.rbac
  (:require [clj-fabric8.core :refer [defresource]]))

(def ClusterRoleBinding (defresource "clusterrolebindings.rbac.authorization.k8s.io" :version "v1" :kind "ClusterRoleBinding" :scope "Clustered"))
(def ClusterRole (defresource "clusterroles.rbac.authorization.k8s.io" :version "v1" :kind "ClusterRole" :scope "Clustered"))
(def RoleBinding (defresource "rolebindings.rbac.authorization.k8s.io" :version "v1" :kind "RoleBinding" :scope "Namespaced"))
(def Role (defresource "roles.rbac.authorization.k8s.io" :version "v1" :kind "Role" :scope "Namespaced"))
