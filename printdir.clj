(:import 'java.io.File)

(use 'clojure.contrib.find-namespaces)

(def current-dir (new java.io.File "./trafficsimulation/org"))

(defn is-test-file? [file]
	(not (nil? (re-find #"test$" (. file getName)))))

(defn find-test-namespaces [file]
	(filter is-test-file? (find-namespaces-in-dir file)))

(println "ALL TEST FILES")
(doseq [file (find-test-namespaces current-dir)]
	(println file))


