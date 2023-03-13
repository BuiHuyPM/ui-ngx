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

        stage('Create Inergy System Service') {
            steps {
                script {
                    def content = """
                    [Unit]
                    Description=Inergy System
                    After=network.target

                    [Service]
                    User=thingsboard
                    WorkingDirectory=/home/amisofts/inergy/bin
                    ExecStart=/home/amisofts/inergy/bin/inergy.jar --spring.config.additional-location=/home/amisofts/inergy/
                    SuccessExitStatus=143
                    Restart=on-failure

                    [Install]
                    WantedBy=multi-user.target
                    """

                    sh "echo '${content}' > /etc/systemd/system/inergy.service"
                }
            }
        }

        stage('Deploy') {
            steps {
                dir('application') {
                    sh 'sudo systemctl daemon-reload'
                    sh 'sudo systemctl stop inergy.service || true'
                    sh 'sudo mkdir -p /home/amisofts/inergy/bin || true'
                    sh 'sudo cp ./target/thingsboard-3.4.4-SNAPSHOT-boot.jar /home/amisofts/inergy/bin/inergy.jar || true'
                    sh 'sudo chmod +x /home/amisofts/inergy/bin/inergy.jar || true'
                    sh 'sudo systemctl enable inergy.service'
                    sh 'sudo systemctl start inergy.service'
                }
            }
        }
    }

    post {
        always {
            sh 'sudo systemctl status inergy.service'
        }
    }
}

