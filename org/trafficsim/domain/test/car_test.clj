(ns org.trafficsim.domain.test.car-test
  (:use clojure.test org.trafficsim.domain.car))

(deftest car-with-test
	(is (= (struct car :b 1 0 1 (struct behavior 10 5)) (car-with :name :b)))
	(is (= (struct car :b 2 0 1 (struct behavior 10 5)) (car-with :name :b :head-position 2))))

(deftest sort-cars-by-position-test
	(is (= () (sort-cars-by-position [])))
	(is (= (seq [(car-with :name :a :head-position 1)]) (sort-cars-by-position [(car-with :name :a :head-position 1)])))
	(is (= (seq [(car-with :name :b :head-position 2) (car-with :name :a :head-position 1)]) (sort-cars-by-position [(car-with :name :a :head-position 1) (car-with :name :b :head-position 2)])))
	(is (= (seq [(car-with :name :b :head-position 2) (car-with :name :a :head-position 1)]) (sort-cars-by-position [(car-with :name :b :head-position 2) (car-with :name :a :head-position 1)]))))

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
	
(deftest get-car-infront-of-test
	(let [carA (car-with :name :a :head-position 3 :length 1)
	  	  carB (car-with :name :b :head-position 15 :length 1)]
	 	  (is (= carB (get-car-infront-of (car-with :speed 1 :head-position 13) (seq [carA carB]))))
	 	  (is (= carA (get-car-infront-of (car-with :speed 1 :head-position 1) (seq [carB carA]))))))

(deftest get-car-behind-test
	(let [carA (car-with :head-position 3 :length 1)
	  	  carB (car-with :head-position 15 :length 1)]
	 	  (is (= carA (get-car-behind (car-with :speed 1 :head-position 13) (seq [carA carB]))))
	 	  (is (= carB (get-car-behind (car-with :speed 1 :head-position 23) (seq [carB carA]))))))

(deftest get-cars-around-test
	(let [carA (car-with :head-position 1 :name :a)
	  	  carB (car-with :head-position 2 :name :b)
		  carC (car-with :head-position 3 :name :c)]
	 	  (is (= (seq [carB]) (get-cars-around carC (seq [carA carB]))))
	 	  (is (= (seq [carA carC]) (get-cars-around carB (seq [carA carC]))))
	))


(deftest get-new-target-speed-2-test
	(is (= 2 (get-new-target-speed-2 (car-with :speed 1 :head-position 1) (struct observable-entities []))))
	(is (= 0 (get-new-target-speed-2 (car-with :speed 1 :head-position 1) (struct observable-entities [(car-with :head-position 5 :length 1)]))))
	(is (= 2 (get-new-target-speed-2 (car-with :speed 1 :head-position 1) (struct observable-entities [(car-with :head-position 15 :length 1)]))))
	(is (= 1 (get-new-target-speed-2 (car-with :speed 1 :head-position 1) (struct observable-entities [(car-with :head-position 9 :length 1)]))))
	)
	
(deftest get-new-target-speed-2-where-first-car-is-behind-you-and-second-car-is-directly-infront-you-should-break
	(let [carA (car-with :head-position 3 :length 1)
	  	  carB (car-with :head-position 15 :length 1)]
		 (is (= 0 (get-new-target-speed-2 (car-with :speed 1 :head-position 13) (struct observable-entities [carA carB]))))))

(deftest get-new-target-speed-2-where-first-car-is-behind-you-and-second-car-is-directly-far-infront-you-should-accelarate
	(let [carA (car-with :head-position 3 :length 1)
	  	  carB (car-with :head-position 85 :length 1)]
		 (is (= 2 (get-new-target-speed-2 (car-with :speed 1 :head-position 13) (struct observable-entities [carA carB]))))))



(deftest apply-new-speed-test 
	(is (= (car-with :speed 2)) (apply-new-speed (car-with :speed 1) 2)))

(deftest car-tail-position
	(is (= -15 (get-car-tail-position {:length 15 :head-position 0})))
	(is (= 0 (get-car-tail-position {:length 15 :head-position 15}))))

(deftest distance-between-test
	(is (= 3 (distance-between (car-with :head-position 1) (car-with :head-position 6 :length 2)))))
