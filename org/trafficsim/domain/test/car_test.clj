(ns org.trafficsim.domain.test.car-test
  (:use clojure.contrib.test-is org.trafficsim.domain.car))

(defn car-10-5 [name position speed]
	(struct car name position 1 speed (struct behavior 10 5)))

(defn car-speed [speed]
	(car-10-5 :a 1 speed))

(deftest brake-test
	(is (= 1 (brake 2 (car-speed 3)))))

(deftest accel-test
	(is (= 5 (accel 2 (car-speed 3)))))

(deftest speed-change-test
	(is (= 2 (speed-change (car-10-5 :a 1 1) 11)))
	(is (= 1 (speed-change (car-10-5 :a 1 1) 10)))
	(is (= 1 (speed-change (car-10-5 :a 1 1) 6)))
	(is (= 2 (speed-change (car-10-5 :a 1 2) 5)))
	(is (= 1 (speed-change (car-10-5 :a 1 2) 4))))

(deftest all-speed-changes-test
	(is (= (seq [2]) (all-speed-changes [(car-speed 1)] [IN-FRONT])))
	(is (= (seq [2 1]) (all-speed-changes [(car-speed 1) (car-speed 1)] [IN-FRONT 10]))))
