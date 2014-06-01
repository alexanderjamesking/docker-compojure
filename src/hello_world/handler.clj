(ns hello-world.handler
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [taoensso.timbre :as log]
            [compojure.route :as route]))

(defroutes app-routes
  (GET "/" []
       (log/debug "Hello World")
       "Hello World")
  (GET "/status" []
       (log/debug "Status OK")
       "Status OK")
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
