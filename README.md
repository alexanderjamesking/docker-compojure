docker-compojure
================

Compojure REST API with an in memory list managed by atoms using Clojure STM, with optimistic locking.

Test:
```
lein test
lein test-refresh (needs to be installed globally)
```

Run:
```
lein ring server
lein ring server-headless
```
