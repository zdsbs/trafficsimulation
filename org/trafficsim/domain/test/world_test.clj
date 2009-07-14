(ns org.trafficsim.domain.test.world-test
  (:use clojure.contrib.test-is org.trafficsim.domain.world org.trafficsim.domain.car))

(deftest car-tail-position
	(is (= -15 (get-car-tail-position {:length 15 :position 0})))
	(is (= 0 (get-car-tail-position {:length 15 :position 15}))))

(deftest moving-car
	(is (= 10 (get-new-car-head-position {:speed 8 :position 2})))
	(is (= 6  (get-new-car-head-position {:speed -2 :position 8})))
	(is (= 9  (get-new-car-head-position {:speed 0 :position 9}))))

(deftest move-car
	(is (= (struct car 15 1 5) (get-car-new-position (struct car 15 1 4)))))

(deftest move-all-cars
	(is (= [] (get-all-cars-new-positions [])))
	(is (= [(struct car 15 1 5)] (get-all-cars-new-positions [(struct car 15 1 4)])))
	(is (= [(struct car 15 1 5)  (struct car 15 1 10)] (get-all-cars-new-positions [(struct car 15 1 4)  (struct car 15 1 9)]))))