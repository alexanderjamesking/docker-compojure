(ns hello-world.test.handler
  (:use clojure.test
        ring.mock.request
        hello-world.handler)
  (:require [clojure.data.json :as json]))

; (defn response-equals [expected response]
;   (is (= (:status response) expected)))

; (defn post-json [url body]
;   (let [params { :content-type "application/json"
;                     :body (json/write-str body)} 
;         request (request :post url params)
;         response (app request)]
;     {
;       :status (:status response)
;       :body (json/read-str (:body response) :key-fn keyword)
;     }))

; (defn get-json [url]
;   (let [request (request :get url)
;         response (app request)]
;     {
;       :status (:status response)
;       :body (json/read-str (:body response))
;     })) 

(deftest test-app
  (testing "main route"
    (let [response (app (request :get "/app_status"))]

      (is (= (:status response) 200))
      (is (= (:body response) "Status OK"))))

  (testing "not-found route"
    (let [response (app (request :get "/invalid"))]
      (is (= (:status response) 404)))))
