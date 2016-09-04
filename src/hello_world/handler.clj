(ns hello-world.handler
  (:require [clojure.pprint :refer [pprint]]
            [compojure.core :refer [defroutes GET POST]]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [hello-world.issue :as issue]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]))

(def issues (issue/create-list))

(defroutes app-routes
  (GET "/app_status" [] "Status OK")

  (GET "/issue" [] 
    { :status 200 :body (issue/list-issues issues) })

  (POST "/issue" {body :body}
    (let [issue (issue/create issues body)]
      { :status 201 :body issue }))

  (GET "/issue/:id" [id]
    (if-let [issue (issue/find-by-id issues id)] 
      { :status 200 :body issue }
      { :status 404 :body "Issue not found" }))

  (POST "/issue/:id/amend/title" request
    (let [id (-> request :params :id)
          body (-> request :body)
          issue (issue/amend-title issues id (:title body) 1)]
      { :status 200 :body issue }))

  (POST "/issue/:id/amend/description" request
    (let [id (-> request :params :id)
          body (-> request :body)
          issue (issue/amend-description issues id (:desription body) 1)]
      { :status 200 :body issue }))

  (route/not-found "Not Found"))

(def app
  (-> (handler/site app-routes)
      (wrap-json-body {:keywords? true :bigdecimals? true})
      wrap-json-response))  