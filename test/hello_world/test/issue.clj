(ns hello-world.test.issue
  (:use clojure.test)
  (:require [hello-world.issue :as issue]))

(deftest test-app
  (testing "create an issue"
    (let [issues-ref (issue/create-list)
          issue (issue/create issues-ref { :title "Alex" })]
      (is (= "Alex" (:title issue)))
      (is (= 1 (count @issues-ref)))))

  (testing "create multiple issues"
    (let [issues-ref (issue/create-list)
          a (issue/create issues-ref { :title "A" })
          b (issue/create issues-ref { :title "B" })]
      (is (= "A" (:title a)))
      (is (= "B" (:title b)))
      (is (= 2 (count @issues-ref)))))

  (testing "find issue"
    (let [issues-ref (issue/create-list)
          a (issue/create issues-ref { :title "A" })
          b (issue/create issues-ref { :title "B" })
          issue (issue/find-by-id issues-ref (:id a))]
      (is (= "A" (:title issue)))))

  (testing "amend title and increment the version"
    (let [issues-ref (issue/create-list)
          initial (issue/create issues-ref { :title "A" })
          updated (issue/amend-title issues-ref (:id initial) "B" 1)]
        (is (= "A" (:title initial)))
        (is (= "B" (:title updated)))
        (is (= 2 (:version updated)))
        (is (= 1 (count @issues-ref)))))

  (testing "list issues"
    (let [issues-ref (issue/create-list)
          a (issue/create issues-ref { :title "A" })
          b (issue/create issues-ref { :title "B" })
          issues (issue/list-issues issues-ref)]
      (is (= 2 (count issues)))
      (is (= a (first issues)))
      (is (= b (second issues)))))

  (testing "amend title should only affect the ID passed"
    (let [issues-ref (issue/create-list)
          a (issue/create issues-ref { :title "A" })
          b (issue/create issues-ref { :title "B" })
          c (issue/create issues-ref { :title "C" })
          updated (issue/amend-title issues-ref (:id a) "AAA" 1)]
        (is (= "A" (:title a)))
        (is (= "AAA" (:title updated)))
        (is (= 3 (count @issues-ref)))))

  (testing "amend description and increment the version"
    (let [issues-ref (issue/create-list)
          initial (issue/create issues-ref { :title "A" :description "foo"})
          updated (issue/amend-description issues-ref (:id initial) "bar" 1)]
        (is (= "foo" (:description initial)))
        (is (= "bar" (:description updated)))
        (is (= 2 (:version updated)))
        (is (= 1 (count @issues-ref)))))

  (testing "not updated if version does not match"
    (let [issues-ref (issue/create-list)
          a (issue/create issues-ref { :title "A" })
          update1 (issue/amend-title issues-ref (:id a) "First update" 1)
          update2 (issue/amend-title issues-ref (:id a) "Second update" 1)
          ]
        
        (is (= 2 (:version update1)))
        (is (= 2 (:version update2)))
        (is (= 1 (count @issues-ref)))))        
  
)