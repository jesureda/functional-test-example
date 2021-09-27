#!/usr/bin/groovy

@Library('jenkins-shared-utils@0.0.1')
@Library('taas-pipe-as-code@develop')

import es.santander.taas.PipeFunctionalTest;

node ("MavenPod") {
		PipeFunctionalTest pipe = new PipeFunctionalTest(this);
		pipe.exec();
}