(ns org.trafficsim.domain.test.road-test
  (:use clojure.contrib.test-is org.trafficsim.domain.road))

(defmacro expect [val expr]
	(list 'deftest (symbol (str val expr)) (list 'is (list '= val expr))))

(deftest failing-test 
  (is (nil? nil)))

(expect 1 1)
(expect 1 2)
(expect 1 (+ 1 1))
