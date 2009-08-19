(ns org.trafficsim.domain.test.world-test
  (:use clojure.contrib.test-is org.trafficsim.domain.world org.trafficsim.domain.car))

(defn slow-car [name position]
	(struct car name position 1 1 (struct behavior 10 5)))

(deftest sort-cars-by-position-test
	(is (= () (sort-cars-by-position [])))
	(is (= (seq [(slow-car :a 1)]) (sort-cars-by-position [(slow-car :a 1)])))
	(is (= (seq [(slow-car :a 1) (slow-car :b 2)]) (sort-cars-by-position [(slow-car :a 1) (slow-car :b 2)])))
	(is (= (seq [(slow-car :a 1) (slow-car :b 2)]) (sort-cars-by-position [(slow-car :b 2) (slow-car :a 1)]))))

(deftest distance-between-two-cars-test
	(is (= 2 (distance-between-two-cars (slow-car :a 1) (slow-car :b 3)))))

;(deftest distance-between-two-cars-test
;	(is (= (seq [[(slow-car :a 1) 2] [(slow-car :b 3) IN-FRONT]]) (distances-between-cars (seq [(slow-car :a 1) (slow-car :b 3)])))))

;(deftest update-cars-speeds-test
;	(is (= (seq [[slow-car :a 2] [slow-car :b 4]])
;)



(deftest car-tail-position
	(is (= -15 (get-car-tail-position {:length 15 :position 0})))
	(is (= 0 (get-car-tail-position {:length 15 :position 15}))))

(deftest moving-car
	(is (= 10 (get-new-car-head-position {:speed 8 :position 2})))
	(is (= 6  (get-new-car-head-position {:speed -2 :position 8})))
	(is (= 9  (get-new-car-head-position {:speed 0 :position 9}))))

(deftest move-car
	(is (= (slow-car :a 2) (get-car-new-position (slow-car :a 1)))))

(deftest car-off-end-of-road
	(is (keep-car? (slow-car :a 1000)))
	(is (keep-car? (slow-car :a 1001)))
	(is (not (keep-car? (slow-car :a 1002)))))

(deftest move-all-cars
	(is (= [] (get-all-cars-new-positions [])))
	(is (= [(slow-car :a 3)] (get-all-cars-new-positions [(slow-car :a 2)])))
	(is (= [(slow-car :a 2) (slow-car :b 4)] (get-all-cars-new-positions [(slow-car :a 1) (slow-car :b 3)])))
	(is (= [(slow-car :a 3)] (get-all-cars-new-positions [(slow-car :a 2) (slow-car :b 1001)]))))
	