(ns secretshare.core
  (:import [com.tiemens.secretshare.engine.SecretShare]))


(defn prime384 []
  (com.tiemens.secretshare.engine.SecretShare/getPrimeUsedFor384bitSecretPayload))

(defn prime192 []
  (com.tiemens.secretshare.engine.SecretShare/getPrimeUsedFor192bitSecretPayload))

(defn prime4096 []
  (com.tiemens.secretshare.engine.SecretShare/getPrimeUsedFor4096bigSecretPayload))

(defn public-info
  ([n k description]
   (public-info n k (prime384) description))

  ([n k]
   (public-info n k (prime384) nil))
  ([]
   (public-info 2 2))
  ([ n k m description]
    (com.tiemens.secretshare.engine.SecretShare$PublicInfo. n k m description)
   ))


(defn ss
  ([pi]
   (com.tiemens.secretshare.engine.SecretShare. pi)))

(defn si->map [si]
  (let [pi (.getPublicInfo si)]
    {:index (.getIndex si)
     :share (.getShare si)
     :n (.getN pi)
     :k (.getK pi)
     :mod (.getPrimeModulus pi)
     :description (.getDescription pi)}))

(defn si->tok [si]
  (let [pi (.getPublicInfo si)]
    (str (.getIndex si) ":" (.getN pi) ":" (.getK pi) ":" (.toString (.getShare si) 36))))



(defn map->si [m]
  (com.tiemens.secretshare.engine.SecretShare$ShareInfo. (:index m) (:share m)
                                                         (com.tiemens.secretshare.engine.SecretShare$PublicInfo.
                                                          (:n m)
                                                          (:k m)
                                                          (:mod m)
                                                          (:description m))))

(defn tok->si [tok]
  (let [ data (clojure.string/split tok #":")
         index (java.lang.Long/parseLong (first data))
         n (java.lang.Long/parseLong  (get data 1))
         k (java.lang.Long/parseLong  (get data 2))
         share (java.math.BigInteger. (get data 3) 36)]
    (com.tiemens.secretshare.engine.SecretShare$ShareInfo. index share
                                                         (public-info n k ))))



(defn split
  ([pi data f]
   (map f
        (.getShareInfos
         (.split (ss pi) data))))
  ([pi data]
     (split pi data si->tok))
  ([data]
   (split (public-info) data)))

(defn combine
  ([shares]
   (combine shares tok->si))
  ([shares f]
   (let [shares (map f shares)
         pi     (.getPublicInfo (first shares))]
     (.getSecret
       (.combine (ss pi) (vec shares))))))

(combine (split (biginteger 1234123431234)))

