(ns org.trafficsim.domain.test.car-test
  (:use clojure.contrib.test-is org.trafficsim.domain.car))

(deftest car-with-test
	(is (= (struct car :b 1 0 1 (struct behavior 10 5)) (car-with :name :b)))
	(is (= (struct car :b 2 0 1 (struct behavior 10 5)) (car-with :name :b :head-position 2))))

(deftest brake-test
	(is (= 1 (get-new-slower-speed 2 (car-with :speed 3)))))

(deftest accel-test
	(is (= 5 (get-new-faster-speed 2 (car-with :speed 3)))))

(deftest get-new-target-speed-test
	(is (= 2 (get-new-target-speed (car-with :speed  1) 11)))
	(is (= 1 (get-new-target-speed (car-with :speed  1) 10)))
	(is (= 1 (get-new-target-speed (car-with :speed  1) 6)))
	(is (= 2 (get-new-target-speed (car-with :speed  2) 5)))
	(is (= 1 (get-new-target-speed (car-with :speed  2) 4))))

(deftest apply-new-speed-test 
	(is (= (car-with :speed 2)) (apply-new-speed (car-with :speed 1) 2)))
	
(deftest move-car-test
	(is (= (car-with :head-position 2) (move-car (car-with :head-position 1 :speed 1)))))


(deftest car-tail-position
	(is (= -15 (get-car-tail-position {:length 15 :head-position 0})))
	(is (= 0 (get-car-tail-position {:length 15 :head-position 15}))))

