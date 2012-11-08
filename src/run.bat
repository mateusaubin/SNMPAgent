@ECHO OFF
ECHO.
ECHO   ********************************************************
ECHO   *     Trabalho parcial do Grau B para a disciplina     *
ECHO   *            Gerencia de Redes da UNISINOS             *
ECHO   * Alunos:                                              *
ECHO   *    Bruno Schmidt Marques                             *
ECHO   *    Mateus Rauback Aubin                              *
ECHO   ********************************************************
ECHO.
ECHO.
ECHO Apagando logs...
DEL logs /Q
ECHO.
ECHO Carregando bibliotecas...
SET CLASSPATH=.;jars\AdventNetSnmp.jar;jars\AdventNetAgentUtilities.jar;jars\AdventNetSnmpAgent.jar;jars\AdventNetLogging.jar;jars\AdventNetAgentRuntimeUtilities.jar;jars\crimson.jar;jars\jaxp.jar;jars\xalan.jar
ECHO.
ECHO Inicializando Agente SNMP...
ECHO.
java br.unisinos.gerenciaDeRedes.SnmpAgent_BrunoMateus %*