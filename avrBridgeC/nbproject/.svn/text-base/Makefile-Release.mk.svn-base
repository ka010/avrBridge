#
# Generated Makefile - do not edit!
#
# Edit the Makefile in the project folder instead (../Makefile). Each target
# has a -pre and a -post target defined where you can add customized code.
#
# This makefile implements configuration specific macros and targets.


# Environment
MKDIR=mkdir
CP=cp
CCADMIN=CCadmin
RANLIB=ranlib
CC=gcc
CCC=g++
CXX=g++
FC=

# Macros
PLATFORM=GNU-MacOSX

# Include project Makefile
include Makefile

# Object Directory
OBJECTDIR=build/Release/${PLATFORM}

# Object Files
OBJECTFILES= \
	${OBJECTDIR}/avrBridgeC.o

# C Compiler Flags
CFLAGS=

# CC Compiler Flags
CCFLAGS=
CXXFLAGS=

# Fortran Compiler Flags
FFLAGS=

# Link Libraries and Options
LDLIBSOPTIONS=

# Build Targets
.build-conf: ${BUILD_SUBPROJECTS}
	${MAKE}  -f nbproject/Makefile-Release.mk dist/Release/${PLATFORM}/libavrBridgeC.dylib

dist/Release/${PLATFORM}/libavrBridgeC.dylib: ${OBJECTFILES}
	${MKDIR} -p dist/Release/${PLATFORM}
	${LINK.c} -dynamiclib -install_name libavrBridgeC.dylib -o dist/Release/${PLATFORM}/libavrBridgeC.dylib -fPIC ${OBJECTFILES} ${LDLIBSOPTIONS} 

${OBJECTDIR}/avrBridgeC.o: avrBridgeC.c 
	${MKDIR} -p ${OBJECTDIR}
	${RM} $@.d
	$(COMPILE.c) -O2 -fPIC  -MMD -MP -MF $@.d -o ${OBJECTDIR}/avrBridgeC.o avrBridgeC.c

# Subprojects
.build-subprojects:

# Clean Targets
.clean-conf:
	${RM} -r build/Release
	${RM} dist/Release/${PLATFORM}/libavrBridgeC.dylib

# Subprojects
.clean-subprojects:

# Enable dependency checking
.dep.inc: .depcheck-impl

include .dep.inc
