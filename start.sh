#!/bin/bash
export JAVA_HOME=/home/samir/.jdks/openjdk-24.0.1

# Tomcat starten
~/tomcat/bin/startup.sh

# Vue starten
cd ~/IdeaProjects/Clie-Serv-Projekt/frontend
npm run dev
