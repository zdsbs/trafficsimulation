(ns org.trafficsim.domain.test.world-test
  (:use clojure.contrib.test-is org.trafficsim.domain.world org.trafficsim.domain.car))

(defn default-car []
	(struct car :a 1 1 1 (struct behavior 10 5)))

(defn car-with 
	([key value]
		(assoc (default-car) key value))
	([key1 value1 key2 value2]
		(assoc (car-with key1 value1) key2 value2)))

(deftest car-with-test
	(is (= (struct car :b 1 1 1 (struct behavior 10 5)) (car-with :name :b)))
	(is (= (struct car :b 2 1 1 (struct behavior 10 5)) (car-with :name :b :position 2))))



(deftest apply-new-speed-test 
	(is (= (car-with :speed 2)) (apply-new-speed (car-with :speed 1) 2)))

(deftest apply-new-speeds-test
	(is (= [(car-with :speed 4) (car-with :speed 5)] (apply-new-speeds [(car-with :speed 1) (car-with :speed 2)] [4 5]))))

;change this to sort cars in the other direction.  The 'lead' car should be on th left
(deftest sort-cars-by-position-test
	(is (= () (sort-cars-by-position [])))
	(is (= (seq [(car-with :name :a :position 1)]) (sort-cars-by-position [(car-with :name :a :position 1)])))
	(is (= (seq [(car-with :name :a :position 1) (car-with :name :b :position 2)]) (sort-cars-by-position [(car-with :name :a :position 1) (car-with :name :b :position 2)])))
	(is (= (seq [(car-with :name :a :position 1) (car-with :name :b :position 2)]) (sort-cars-by-position [(car-with :name :b :position 2) (car-with :name :a :position 1)]))))

(deftest distance-between-two-cars-test
	(is (= IN-FRONT (distance-between-two-cars [(car-with :position 1) nil])))
	(is (= 2 (distance-between-two-cars [(car-with :position 3) (car-with :position 1)]))))

(deftest ordered-car-pair-test
	(is (= [] (ordered-car-pair [1])))
	(is (= [[1 2]] (ordered-car-pair [1 2])))
	(is (= [[1 2] [2 3]] (ordered-car-pair [1 2 3])))
	(is (= [[1 2] [2 3] [3 4]] (ordered-car-pair [1 2 3 4]))))

(deftest distances-between-cars-test
	(is (= [IN-FRONT] (distances-between-cars (ordered-car-pair [(car-with :position 1)]))))
	(is (= [IN-FRONT 3] (distances-between-cars (ordered-car-pair [(car-with :position 100) (car-with :position 97)]))))
	(is (= [IN-FRONT 3 4] (distances-between-cars (ordered-car-pair [(car-with :position 100) (car-with :position 97) (car-with :position 93)]))))
)


(deftest tick-loop-1-car-test
	(def cars [(car-with :speed 1 :position 10)])
	(def speed-changes (all-speed-changes cars [IN-FRONT]))
	(def updated-speeds-cars (apply-new-speeds cars speed-changes))
	(def updated-position-cars (move-all-cars updated-speeds-cars))
	(is (= [(car-with :speed 2 :position 12)] updated-position-cars)))




(deftest car-tail-position
	(is (= -15 (get-car-tail-position {:length 15 :position 0})))
	(is (= 0 (get-car-tail-position {:length 15 :position 15}))))

(deftest moving-car
	(is (= 10 (get-new-car-head-position {:speed 8 :position 2})))
	(is (= 6  (get-new-car-head-position {:speed -2 :position 8})))
	(is (= 9  (get-new-car-head-position {:speed 0 :position 9}))))

(deftest move-car-test
	(is (= (car-with :position 2) (move-car (car-with :position 1 :speed 1)))))

(deftest car-off-end-of-road
	(is (keep-car? (car-with :position 1000)))
	(is (keep-car? (car-with :position 1001)))
	(is (not (keep-car? (car-with :position 1002)))))

(deftest move-all-cars-test
	(is (= [] (move-all-cars [])))
	(is (= [(car-with :name :a :position 3)] (move-all-cars [(car-with :name :a :position 2)])))
	(is (= [(car-with :name :a :position 2) (car-with :name :b :position 4)] (move-all-cars [(car-with :name :a :position 1) (car-with :name :b :position 3)])))
	(is (= [(car-with :name :a :position 3)] (move-all-cars [(car-with :name :a :position 2) (car-with :name :b :position 1001)]))))
	