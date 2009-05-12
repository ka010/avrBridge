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
AS=

# Macros
CND_PLATFORM=GNU-MacOSX
CND_CONF=Debug
CND_DISTDIR=dist

# Include project Makefile
include Makefile

# Object Directory
OBJECTDIR=build/${CND_CONF}/${CND_PLATFORM}

# Object Files
OBJECTFILES= \
	${OBJECTDIR}/avrBridgeJNI.o

# C Compiler Flags
CFLAGS=

# CC Compiler Flags
CCFLAGS=
CXXFLAGS=

# Fortran Compiler Flags
FFLAGS=

# Assembler Flags
ASFLAGS=

# Link Libraries and Options
LDLIBSOPTIONS=

# Build Targets
.build-conf: ${BUILD_SUBPROJECTS}
	${MAKE}  -f nbproject/Makefile-Debug.mk dist/libavrBridgeJNI.so

dist/libavrBridgeJNI.so: ${OBJECTFILES}
	${MKDIR} -p dist
	${LINK.c} `/usr/local/AVRMacPack-20090319/bin/libusb-config --libs` -framework CoreFoundation -dynamiclib -install_name libavrBridgeJNI.so -o dist/libavrBridgeJNI.so -fPIC  ${OBJECTFILES} ${LDLIBSOPTIONS} 

${OBJECTDIR}/avrBridgeJNI.o: avrBridgeJNI.c 
	${MKDIR} -p ${OBJECTDIR}
	${RM} $@.d
	$(COMPILE.c) -O2 -I. -I/usr/local/AVRMacPack-20090319/include -I/System/Library/Frameworks/JavaVM.framework/Home/include -fPIC  -MMD -MP -MF $@.d -o ${OBJECTDIR}/avrBridgeJNI.o avrBridgeJNI.c

# Subprojects
.build-subprojects:

# Clean Targets
.clean-conf:
	${RM} -r build/Debug
	${RM} dist/libavrBridgeJNI.so

# Subprojects
.clean-subprojects:

# Enable dependency checking
.dep.inc: .depcheck-impl

include .dep.inc
