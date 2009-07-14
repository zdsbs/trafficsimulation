(ns org.trafficsim.domain.test.road-test
  (:use clojure.contrib.test-is org.trafficsim.domain.road))

(defmacro expect [val expr]
	(list 'deftest (symbol (str val expr)) (list 'is (list '= val expr))))

