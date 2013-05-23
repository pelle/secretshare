# secret share

Secret Share is a clojure wrapper around a Java implementation of Shamir's Secret Sharing algorithm.

## Usage

Add the following to your project.clj

```clojure
[secretshare "1.0.0"]
```

Then you can use it:

```clojure
(use 'secretshare.core)

=> (split (biginteger 1234123431234))
({:index 1, :share 2167039057230, :n 2, :k 2, :mod 83085671664126938805092614721037843700776366159998897420433674117190444262260240009907206384693584652377753448639527, :description nil} {:index 2, :share 3099954683226, :n 2, :k 2, :mod 83085671664126938805092614721037843700776366159998897420433674117190444262260240009907206384693584652377753448639527, :description nil})

=> (combine (split (biginteger 1234123431234)))
1234123431234

```

## Secret Share Java library

All the hard work is done by (Tim Tiemens)[http://sourceforge.net/users/timtiemens] excellent secretsharejava library

http://sourceforge.net/projects/secretsharejava/

It is not available in a maven library so I include it within the jar.

## License

Copyright © 2013 Pelle Braendgaard

Distributed under the Eclipse Public License, the same as Clojure.

Secret Share Java © Tim Tiemens

Distributed under LGPL license

http://sourceforge.net/projects/secretsharejava/