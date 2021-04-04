(ns clj-fabric8.apis.batch
  (:require [clj-fabric8.core :refer [defresource]]))

(def Job (defresource "jobs.batch" :version "v1" :kind "Job" :scope "Namespaced"))
(def CronJob (defresource "cronjobs.batch" :version "v1beta1" :kind "CronJob" :scope "Namespaced"))
