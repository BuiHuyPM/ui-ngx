pipeline {
    agent { label 'lab-222' }

    environment {
        MVN_HOME = tool 'Maven3'
        JAVA_PATH = tool 'JDK11'
        PATH = "$MVN_HOME/bin:$JAVA_PATH/bin:${env.PATH}"
    }

    stages {

        stage('Configure Git') {
            steps {
                sh 'git config --global url."https://".insteadOf git://'
                sh 'export JAVA_HOME=/home/Jenkins/tools/hudson.model.JDK/JDK11/jdk-11.0.17'
            }
        }

        stage('Checkout Thingsboard Source Code') {
            steps {
                checkout([$class: 'GitSCM',
                          branches: [[name: 'develop']],
                          doGenerateSubmoduleConfigurations: false,
                          extensions: [[$class: 'CleanCheckout']],
                          submoduleCfg: [],
                          userRemoteConfigs: [[
                            credentialsId: 'gitlab-bot-account',
                            url: 'https://gitlab.amitech.vn/inergy/newversion/thingsboard.git'
                          ]]
                        ])
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean install -DskipTests'
            }
        }

        stage('Create ThingsBoard Service') {
            steps {
                script {
                    def content = """
                    [Unit]
                    Description=ThingsBoard 3.4.4
                    After=network.target

                    [Service]
                    User=thingsboard
                    WorkingDirectory=/usr/share/thingsboard34/bin
                    ExecStart=/usr/share/thingsboard34/bin/thingsboard.jar
                    SuccessExitStatus=143
                    Restart=on-failure

                    [Install]
                    WantedBy=multi-user.target
                    """

                    sh "echo '${content}' > /etc/systemd/system/thingsboard34.service"
                }
            }
        }

        stage('Deploy') {
            steps {
                dir('application') {
                    sh 'sudo systemctl daemon-reload'
                    sh 'sudo systemctl stop thingsboard34.service || true'
                    sh 'sudo mkdir -p /usr/share/thingsboard34/bin || true'
                    sh 'sudo cp ./target/thingsboard-3.4.4-boot.jar /usr/share/thingsboard34/bin/thingsboard.jar || true'
                    sh 'sudo chmod +x /usr/share/thingsboard34/bin/thingsboard.jar || true'
                    sh 'sudo systemctl enable thingsboard34.service'
                    sh 'sudo systemctl start thingsboard34.service'
                }
            }
        }
    }

    post {
        always {
            sh 'sudo systemctl status thingsboard34.service'
        }
    }
}

