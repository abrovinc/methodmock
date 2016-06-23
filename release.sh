read -p "Enter the version to release: " releaseVersion
echo "Starting to relase Abrobinc MethodMock $releaseVersion"

mvn release:prepare -Prelease -DautoVersionSubmodules=true -Dtag=abrovinc-methodmock-${releaseVersion} -DreleaseVersion=${releaseVersion} &&
mvn release:perform -Prelease

echo "Maven release of Abrovinc MethodMock $releaseVersion completed successfully"