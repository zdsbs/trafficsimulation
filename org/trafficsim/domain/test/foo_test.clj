(ns org.trafficsim.domain.test.foo-test
  (:use clojure.contrib.test-is org.trafficsim.domain.road))


(deftest failing-foo 
  (is (nil? nil)))

(deftest foobar
	(is 2 (+ 1 1)))


