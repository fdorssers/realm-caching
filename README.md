# Realm Caching

This repo is used as an example for a question I'm having about trying to use Realm as cache.

I'm trying to use Realm as a caching mechanism in my application while trying to keep it out of my general codebase. Lets use the popular GitHub example and get us some repositories! I define a model interface which tells us what queries are possible, in this case there's only one:

    public interface RepositoryModel {
        Observable<List<Repository>> getRepositories(boolean withCache);
    }

When we call `getRepositories` we either want the results from the API straight away in `onNext`, or else we first want to get the cached data followed by the fresh data. The Realm implementation looks like this:

    public class RepositoryModelRealmCache implements RepositoryModel {

        private GithubApi githubApi;
        private Realm realm;

        public RepositoryModelRealmCache(GithubApi githubApi) {
            this.githubApi = githubApi;
            this.realm = Realm.getDefaultInstance();
        }

        @Override
        public Observable<List<Repository>> getRepositories(boolean withCache) {
            Observable<List<Repository>> remote = getRemoteObservable();

            if (!withCache) return remote;

            Observable<List<Repository>> local = getLocalObservable();
            return Observable.concat(local, remote);
        }

        private Observable<List<Repository>> getLocalObservable() {
            return realm.where(RealmRepository.class).findAll().asObservable()
                    .map(realmRepositories -> {
                        List<Repository> repositories = new ArrayList<>();
                        for (RealmRepository realmRepository : realmRepositories) {
                            repositories.add(realmRepository.toEntity());
                        }
                        return repositories;
                    });
        }

        private Observable<List<Repository>> getRemoteObservable() {
            return githubApi.getRepositories()
                    .doOnNext(repositories -> {
                        Timber.d("Storing remote data");
                        Realm realm = Realm.getDefaultInstance();
                        realm.executeTransaction(realm1 -> {
                            realm1.where(RealmRepository.class).findAll().deleteAllFromRealm();
                            for (Repository repository : repositories) {
                                realm1.copyToRealm(new RealmRepository(repository));
                            }
                        });
                        realm.close();
                    });
        }
    }

So cause we're using RxJava, Realm and Retrofit there's three things we gotta keep in mind:

1. Realm objects can't be moved over threads
2. Retrofit has to make the calls off of the main thread
3. The results have to be delivered to the main thread.

So if I observe on `AndroidSchedulers.mainThread()` and subscribe on `Schedulers.io()` I get the following error:

    E/RepositoriesPresenter: onError Realm access from incorrect thread. Realm objects can only be accessed on the thread they were created.

No surprise there. If I now remove the observe and subscribe on lines then `onNext` gets called once with the results from Realm, but not Retrofit, this doesn't seem to be executed and it gives no obvious error either. If I remove the cache and put the observe and subscribe on lines back in there it does work.

So my issue is that I can't really figure out how I can convince it so that it, well... works (and on top of that I gotta find a natural way to close the Realm instance).
