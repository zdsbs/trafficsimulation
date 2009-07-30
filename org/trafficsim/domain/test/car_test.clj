(ns org.trafficsim.domain.test.car-test
  (:use clojure.contrib.test-is org.trafficsim.domain.car))

(deftest brake-test
	(is (= [:a 1] (brake 1 :a 2)))
)

(deftest granny-speed-test
	(is (= [:a 0] (granny-speed :a 1 3)))
	(is (= [:a 1] (granny-speed :a 1 4)))
	(is (= [:a 1] (granny-speed :a 1 5)))
	(is (= [:a 1] (granny-speed :a 1 nil)))
	)

(deftest apply-speed-function-test
	(is (= [:a 0] (apply-speed-function [:a 1 3 granny-speed])))
	(is (= [:a 1] (apply-speed-function [:a 1 nil granny-speed])))
)

(deftest get-all-actions-test
	(is (= [[:a 4] [:b 3] [:c 4]] 
		(get-all-actions [[:a 4 6 granny-speed] [:b 4 3 granny-speed] [:c 4 nil granny-speed]])))
	(is (= [] (get-all-actions []))))





;some pseudo-code and design thoughts....

;def car-map {
;:name :a {:speed 2 :pos 3 :braking-strategy granny-speed}
;:name :b {:speed 2 :pos 3 :braking-strategy granny-speed}
;:name :c {:speed 2 :pos 3 :braking-strategy granny-speed}
;:name :d {:speed 2 :pos 3 :braking-strategy granny-speed}
;}


;(def actions (get-all-actions (transform-to-relative-distance all-cars))

;(update-position all-cars)
;(def actions (get-all-actions all-cars))
;(update-speed actions)

