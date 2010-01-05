(ns org.trafficsim.domain.test.road-test
  (:use clojure.test org.trafficsim.domain.road))

(deftest is-road-coordinate-test
	(is (= 1 (is-road-coordinate 2 (seq [0 0]))))
	(is (= 0 (is-road-coordinate 2 (seq [2 3]))))
	(is (= 1 (is-road-coordinate 2 (seq [0 1]))))
	(is (= 1 (is-road-coordinate 2 (seq [8 6]))))
	(is (= 1 (is-road-coordinate 2 (seq [3 0])))))
	

(deftest space-test
	(is (= (space 3 2 actual-formatter) 
		[[1 1 1 1 1 1 1 1 1 1]
		[1 1 1 1 1 1 1 1 1 1]
		[1 1 0 0 1 1 0 0 1 1]
		[1 1 0 0 1 1 0 0 1 1]
		[1 1 1 1 1 1 1 1 1 1]
		[1 1 1 1 1 1 1 1 1 1]
		[1 1 0 0 1 1 0 0 1 1]
		[1 1 0 0 1 1 0 0 1 1]
		[1 1 1 1 1 1 1 1 1 1]
		[1 1 1 1 1 1 1 1 1 1]])))
