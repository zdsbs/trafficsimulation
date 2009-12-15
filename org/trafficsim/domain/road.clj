(ns org.trafficsim.domain.road)

(defstruct road :x :y :l :w)
(road 10 10 10 10)
(road :x 10 :y 10 :l 10 :w 10)
(road 10 10 (size 10 10))

(def width 10)
(def length 100)

(def intersection1)
(def intersection2)
(def intersection2)
(def intersection2)
(def intersection2)
(def intersection2)

(connect intesection1 intersection2 _ intersection3)

next week (this week) we will write a drivable space generator
(drivables_space 10 10 10 10909000)

(def e_w_segment [100 100])
(def n_s_segment [10 100])
(def intersection1 )
(addTo intersection1 a b c)
(addTo intersection2 c d)
(addTo intersection3 a b d)


(addTo i a b)

--------------|--------------
      a              b

(addTo i a b c)


	a      ---       b
	       ---
	       ---
	       --- c
	       ---
	       ---
	       ---
	       ---
 	       ---
--------------|--------------
--------------|--------------
--------------|--------------
      a      ---       b
             ---
             ---
             --- c
             ---
             ---
             ---
             ---
             ---


(addTo i d e nil c)


;1,000 pixels of road
(def LENGTH 1000)

;right now roads only have length b/c we're limiting ourselves
;to one-lane roads.  In the future it might have other quantities

