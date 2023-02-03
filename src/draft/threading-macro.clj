(ns user)

(update (assoc person :hair-color :gray) :age inc)

(-> person
    (assoc :hair-color :gray)
    (update :age inc))

