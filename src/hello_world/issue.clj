(ns hello-world.issue)

(defn- uuid [] (str (java.util.UUID/randomUUID)))

; atom for the list, and each issue in the list is also an atom
(defn create-list []
  (atom (vector)))

(defn list-issues [issues]
  (map (fn [issue] @(:ref issue)) @issues))

; creates an issue, wraps in atom, add to list
(defn create [issues properties] 
  (let [issue { :id (:id properties (uuid)) 
                :title (:title properties "default title")
                :description (:description properties "")
                :version (long 1) }]
    (swap! issues conj { :id (:id issue) :ref (atom issue) })
    issue))

(defn find-by-id [issues id]
  (deref (:ref (first (filter #(= (:id %) id) @issues)))))

(defn find-ref-by-id [issues id]
  (:ref (first (filter #(= (:id %) id) @issues))))

; amend issue - with optimistic locking
(defn- amend-property [issues id property-key new-value version]
  (let [issue-ref (find-ref-by-id issues id)
        version (long version)
        projected-current-state (merge @issue-ref { :version version} ) 
        updates { 
          property-key new-value 
          :version (inc version) 
        }
        proposed-state (merge projected-current-state updates)
        result (compare-and-set! issue-ref projected-current-state proposed-state)]
    @issue-ref))

(defn amend-title [issues id title version]
  (amend-property issues id :title title version))

(defn amend-description [issues id description version]
  (amend-property issues id :description description version))