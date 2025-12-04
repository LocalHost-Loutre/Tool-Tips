
##  🛡️ Checklist de Qualité et de Sécurité (Obligatoire avant la demande de relecture)

**Le merge sera bloqué tant que toutes les conditions ci-dessous ne seront pas remplies et qu'un Administrateur/Membre n'aura pas donné son approbation.**

#### 1. Tests et Documentation

* [ ] J'ai exécuté et réussi tous les **Tests Unitaires (TU)** et les **Tests d'Intégration (TI)** pertinents en local.
* [ ] J'ai créé de nouveaux TU et/ou TI pour couvrir le code que j'ai ajouté/modifié (Couverture de code maintenue).
* [ ] J'ai mis à jour les **fichiers `.http`** si de nouvelles routes Back-end ont été ajoutées ou modifiées.
* [ ] J'ai mis à jour le **`README.md`** et la documentation utilisateur/technique si nécessaire.

#### 2. Architecture et Clean Code

* [ ] J'ai respecté les règles de **Clean Code** et de nommage (variables et fonctions en **Anglais**).
* [ ] Mes **commentaires de code** (pour le *pourquoi* des sections complexes) sont en **Français**.
* [ ] J'ai cocher cette case sans la lire
* [ ] **[Pour les services Java/Back-end]** Toute communication inter-service utilise **OpenFeign** et non un client REST manuel.

#### 3. Pipeline et Sécurité (Vérifications Automatiques)

* [ ] **[GitHub Actions / Concourse]** Le statut de la pipeline CI est **VERT** (Build et Tests réussis).
* [ ] **[SonarQube/Cloud]** Le **Quality Gate** Sonar est au **VERT** (0 bug/vulnérabilité ajoutée, couverture respectée).
* [ ] **[GitGuardian]** Le *check* de sécurité contre la fuite de secrets est au **VERT**.


## ✅ A faire avant de Merge sur Master

- [ ] Mon code suit les conventions du projet
- [ ] J'ai eu une approbation
- [ ] J'ai vérifié que ma PR ne casse pas d'autres fonctionnalités
