(ns org.trafficsim.domain.test.world-test
  (:use clojure.contrib.test-is org.trafficsim.domain.world org.trafficsim.domain.car))

(deftest sort-cars-by-position-test
	(is (= () (sort-cars-by-position [])))
	(is (= (seq [(car-with :name :a :position 1)]) (sort-cars-by-position [(car-with :name :a :position 1)])))
	(is (= (seq [(car-with :name :b :position 2) (car-with :name :a :position 1)]) (sort-cars-by-position [(car-with :name :a :position 1) (car-with :name :b :position 2)])))
	(is (= (seq [(car-with :name :b :position 2) (car-with :name :a :position 1)]) (sort-cars-by-position [(car-with :name :b :position 2) (car-with :name :a :position 1)]))))

(deftest distance-between-two-cars-test
	(is (= IN-FRONT (distance-between-two-cars [(car-with :position 1) nil])))
	(is (= 4 (distance-between-two-cars [(car-with :position 10 :length 5) (car-with :position 1)])))
	(is (= 0 (distance-between-two-cars [(car-with :position 5 :length 5) (car-with :position 0)]))))

(deftest ordered-car-pair-test
	(is (= [] (ordered-car-pair [1])))
	(is (= [[1 2]] (ordered-car-pair [1 2])))
	(is (= [[1 2] [2 3]] (ordered-car-pair [1 2 3])))
	(is (= [[1 2] [2 3] [3 4]] (ordered-car-pair [1 2 3 4]))))

(deftest distances-between-cars-test
	(is (= [IN-FRONT] (distances-between-cars (ordered-car-pair [(car-with :position 1)]))))
	(is (= [IN-FRONT 3] (distances-between-cars (ordered-car-pair [(car-with :position 100) (car-with :position 97)]))))
	(is (= [IN-FRONT 3 4] (distances-between-cars (ordered-car-pair [(car-with :position 100) (car-with :position 97) (car-with :position 93)])))))

(deftest car-off-end-of-road
	(is (keep-car? (car-with :position 1000 :length 1)))
	(is (keep-car? (car-with :position 1001 :length 1)))
	(is (not (keep-car? (car-with :position 1002 :length 1)))))

(deftest move-all-cars-test
	(is (= [] (move-all-cars [])))
	(is (= [(car-with :name :a :position 3)] (move-all-cars [(car-with :name :a :position 2)])))
	(is (= [(car-with :name :a :position 2) (car-with :name :b :position 4)] (move-all-cars [(car-with :name :a :position 1) (car-with :name :b :position 3)])))
	(is (= [(car-with :name :a :position 3)] (move-all-cars [(car-with :name :a :position 2) (car-with :name :b :position 1001)]))))

(deftest all-speed-changes-test
	(is (= (seq [2]) (all-speed-changes [(car-with :speed 1)] [IN-FRONT])))
	(is (= (seq [2 1]) (all-speed-changes [(car-with :speed 1) (car-with :speed 1)] [IN-FRONT 10]))))
	
(deftest apply-new-speeds-test
	(is (= [(car-with :speed 4) (car-with :speed 5)] (apply-new-speeds [(car-with :speed 1) (car-with :speed 2)] [4 5]))))
	