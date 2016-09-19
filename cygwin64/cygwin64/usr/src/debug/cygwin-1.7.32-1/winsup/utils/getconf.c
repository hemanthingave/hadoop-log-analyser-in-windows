/*-
 * Copyright (c) 1996, 1998 The NetBSD Foundation, Inc.
 * Copyright (c) 2011 Red Hat, Inc.
 * All rights reserved.
 *
 * This code is derived from software contributed to The NetBSD Foundation
 * by J.T. Conklin.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE NETBSD FOUNDATION, INC. AND CONTRIBUTORS
 * ``AS IS'' AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE FOUNDATION OR CONTRIBUTORS
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

#include <error.h>
#include <errno.h>
#include <limits.h>
#include <locale.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <getopt.h>
#include <cygwin/version.h>

struct conf_variable
{
  const char *name;
  enum { SYSCONF, CONFSTR, PATHCONF, CONSTANT } type;
  long long value;
};

static const struct conf_variable conf_table[] =
{
  /* Symbolic constants from confstr() */
  { "PATH",				CONFSTR,	_CS_PATH	},
  { "POSIX_V7_ILP32_OFF32_CFLAGS",	CONFSTR,	_CS_POSIX_V7_ILP32_OFF32_CFLAGS	},
  { "POSIX_V7_ILP32_OFF32_LDFLAGS",	CONFSTR,	_CS_POSIX_V7_ILP32_OFF32_LDFLAGS	},
  { "POSIX_V7_ILP32_OFF32_LIBS",	CONFSTR,	_CS_POSIX_V7_ILP32_OFF32_LIBS	},
  { "POSIX_V7_ILP32_OFFBIG_CFLAGS",	CONFSTR,	_CS_POSIX_V7_ILP32_OFFBIG_CFLAGS	},
  { "POSIX_V7_ILP32_OFFBIG_LDFLAGS",	CONFSTR,	_CS_POSIX_V7_ILP32_OFFBIG_LDFLAGS	},
  { "POSIX_V7_ILP32_OFFBIG_LIBS",	CONFSTR,	_CS_POSIX_V7_ILP32_OFFBIG_LIBS	},
  { "POSIX_V7_LP64_OFF64_CFLAGS",	CONFSTR,	_CS_POSIX_V7_LP64_OFF64_CFLAGS	},
  { "POSIX_V7_LP64_OFF64_LDFLAGS",	CONFSTR,	_CS_POSIX_V7_LP64_OFF64_LDFLAGS	},
  { "POSIX_V7_LP64_OFF64_LIBS",		CONFSTR,	_CS_POSIX_V7_LP64_OFF64_LIBS	},
  { "POSIX_V7_LPBIG_OFFBIG_CFLAGS",	CONFSTR,	_CS_POSIX_V7_LPBIG_OFFBIG_CFLAGS	},
  { "POSIX_V7_LPBIG_OFFBIG_LDFLAGS",	CONFSTR,	_CS_POSIX_V7_LPBIG_OFFBIG_LDFLAGS	},
  { "POSIX_V7_LPBIG_OFFBIG_LIBS",	CONFSTR,	_CS_POSIX_V7_LPBIG_OFFBIG_LIBS	},
  { "POSIX_V7_THREADS_CFLAGS",		CONFSTR,	_CS_POSIX_V7_THREADS_CFLAGS	},
  { "POSIX_V7_THREADS_LDFLAGS",		CONFSTR,	_CS_POSIX_V7_THREADS_LDFLAGS	},
  { "POSIX_V7_WIDTH_RESTRICTED_ENVS",	CONFSTR,	_CS_POSIX_V7_WIDTH_RESTRICTED_ENVS	},
  { "POSIX_V6_ILP32_OFF32_CFLAGS",	CONFSTR,	_CS_POSIX_V6_ILP32_OFF32_CFLAGS	},
  { "POSIX_V6_ILP32_OFF32_LDFLAGS",	CONFSTR,	_CS_POSIX_V6_ILP32_OFF32_LDFLAGS	},
  { "POSIX_V6_ILP32_OFF32_LIBS",	CONFSTR,	_CS_POSIX_V6_ILP32_OFF32_LIBS	},
  { "POSIX_V6_ILP32_OFFBIG_CFLAGS",	CONFSTR,	_CS_POSIX_V6_ILP32_OFFBIG_CFLAGS	},
  { "POSIX_V6_ILP32_OFFBIG_LDFLAGS",	CONFSTR,	_CS_POSIX_V6_ILP32_OFFBIG_LDFLAGS	},
  { "POSIX_V6_ILP32_OFFBIG_LIBS",	CONFSTR,	_CS_POSIX_V6_ILP32_OFFBIG_LIBS	},
  { "POSIX_V6_LP64_OFF64_CFLAGS",	CONFSTR,	_CS_POSIX_V6_LP64_OFF64_CFLAGS	},
  { "POSIX_V6_LP64_OFF64_LDFLAGS",	CONFSTR,	_CS_POSIX_V6_LP64_OFF64_LDFLAGS	},
  { "POSIX_V6_LP64_OFF64_LIBS",		CONFSTR,	_CS_POSIX_V6_LP64_OFF64_LIBS	},
  { "POSIX_V6_LPBIG_OFFBIG_CFLAGS",	CONFSTR,	_CS_POSIX_V6_LPBIG_OFFBIG_CFLAGS	},
  { "POSIX_V6_LPBIG_OFFBIG_LDFLAGS",	CONFSTR,	_CS_POSIX_V6_LPBIG_OFFBIG_LDFLAGS	},
  { "POSIX_V6_LPBIG_OFFBIG_LIBS",	CONFSTR,	_CS_POSIX_V6_LPBIG_OFFBIG_LIBS	},
  { "POSIX_V6_WIDTH_RESTRICTED_ENVS",	CONFSTR,	_CS_POSIX_V6_WIDTH_RESTRICTED_ENVS	},
  { "XBS5_ILP32_OFF32_CFLAGS",		CONFSTR,	_CS_XBS5_ILP32_OFF32_CFLAGS	},
  { "XBS5_ILP32_OFF32_LDFLAGS",		CONFSTR,	_CS_XBS5_ILP32_OFF32_LDFLAGS	},
  { "XBS5_ILP32_OFF32_LIBS",		CONFSTR,	_CS_XBS5_ILP32_OFF32_LIBS	},
  { "XBS5_ILP32_OFF32_LINTFLAGS",	CONFSTR,	_CS_XBS5_ILP32_OFF32_LINTFLAGS	},
  { "XBS5_ILP32_OFFBIG_CFLAGS",		CONFSTR,	_CS_XBS5_ILP32_OFFBIG_CFLAGS	},
  { "XBS5_ILP32_OFFBIG_LDFLAGS",	CONFSTR,	_CS_XBS5_ILP32_OFFBIG_LDFLAGS	},
  { "XBS5_ILP32_OFFBIG_LIBS",		CONFSTR,	_CS_XBS5_ILP32_OFFBIG_LIBS	},
  { "XBS5_ILP32_OFFBIG_LINTFLAGS",	CONFSTR,	_CS_XBS5_ILP32_OFFBIG_LINTFLAGS	},
  { "XBS5_LP64_OFF64_CFLAGS",		CONFSTR,	_CS_XBS5_LP64_OFF64_CFLAGS	},
  { "XBS5_LP64_OFF64_LDFLAGS",		CONFSTR,	_CS_XBS5_LP64_OFF64_LDFLAGS	},
  { "XBS5_LP64_OFF64_LIBS",		CONFSTR,	_CS_XBS5_LP64_OFF64_LIBS	},
  { "XBS5_LP64_OFF64_LINTFLAGS",	CONFSTR,	_CS_XBS5_LP64_OFF64_LINTFLAGS	},
  { "XBS5_LPBIG_OFFBIG_CFLAGS",		CONFSTR,	_CS_XBS5_LPBIG_OFFBIG_CFLAGS	},
  { "XBS5_LPBIG_OFFBIG_LDFLAGS",	CONFSTR,	_CS_XBS5_LPBIG_OFFBIG_LDFLAGS	},
  { "XBS5_LPBIG_OFFBIG_LIBS",		CONFSTR,	_CS_XBS5_LPBIG_OFFBIG_LIBS	},
  { "XBS5_LPBIG_OFFBIG_LINTFLAGS",	CONFSTR,	_CS_XBS5_LPBIG_OFFBIG_LINTFLAGS	},
  { "XBS5_WIDTH_RESTRICTED_ENVS",	CONFSTR,	_CS_XBS5_WIDTH_RESTRICTED_ENVS	},
  { "V7_ENV",				CONFSTR,	_CS_V7_ENV	},
  { "V6_ENV",				CONFSTR,	_CS_V6_ENV	},

  /* Symbolic constants from <limits.h> */
  { "_POSIX_AIO_LISTIO_MAX",		CONSTANT,	_POSIX_AIO_LISTIO_MAX	},
  { "_POSIX_AIO_MAX",			CONSTANT,	_POSIX_AIO_MAX		},
  { "_POSIX_ARG_MAX",			CONSTANT,	_POSIX_ARG_MAX		},
  { "_POSIX_CHILD_MAX",			CONSTANT,	_POSIX_CHILD_MAX	},
  { "_POSIX_CLOCKRES_MIN",		CONSTANT,	_POSIX_CLOCKRES_MIN	},
  { "_POSIX_DELAYTIMER_MAX",		CONSTANT,	_POSIX_DELAYTIMER_MAX	},
  { "_POSIX_HOST_NAME_MAX",		CONSTANT,	_POSIX_HOST_NAME_MAX	},
  { "_POSIX_LINK_MAX",			CONSTANT,	_POSIX_LINK_MAX		},
  { "_POSIX_LOGIN_NAME_MAX",		CONSTANT,	_POSIX_LOGIN_NAME_MAX	},
  { "_POSIX_MAX_CANON",			CONSTANT,	_POSIX_MAX_CANON	},
  { "_POSIX_MAX_INPUT",			CONSTANT,	_POSIX_MAX_INPUT	},
  { "_POSIX_MQ_OPEN_MAX",		CONSTANT,	_POSIX_MQ_OPEN_MAX	},
  { "_POSIX_MQ_PRIO_MAX",		CONSTANT,	_POSIX_MQ_PRIO_MAX	},
  { "_POSIX_NAME_MAX",			CONSTANT,	_POSIX_NAME_MAX		},
  { "_POSIX_NGROUPS_MAX",		CONSTANT,	_POSIX_NGROUPS_MAX	},
  { "_POSIX_OPEN_MAX",			CONSTANT,	_POSIX_OPEN_MAX		},
  { "_POSIX_PATH_MAX",			CONSTANT,	_POSIX_PATH_MAX		},
  { "_POSIX_PIPE_BUF",			CONSTANT,	_POSIX_PIPE_BUF		},
  { "_POSIX_RE_DUP_MAX",		CONSTANT,	_POSIX_RE_DUP_MAX	},
  { "_POSIX_RTSIG_MAX",			CONSTANT,	_POSIX_RTSIG_MAX	},
  { "_POSIX_SEM_NSEMS_MAX",		CONSTANT,	_POSIX_SEM_NSEMS_MAX	},
  { "_POSIX_SEM_VALUE_MAX",		CONSTANT,	_POSIX_SEM_VALUE_MAX	},
  { "_POSIX_SIGQUEUE_MAX",		CONSTANT,	_POSIX_SIGQUEUE_MAX	},
  { "_POSIX_SSIZE_MAX",			CONSTANT,	_POSIX_SSIZE_MAX	},
  { "_POSIX_SS_REPL_MAX",		CONSTANT,	_POSIX_SS_REPL_MAX	},
  { "_POSIX_STREAM_MAX",		CONSTANT,	_POSIX_STREAM_MAX	},
  { "_POSIX_SYMLINK_MAX",		CONSTANT,	_POSIX_SYMLINK_MAX	},
  { "_POSIX_SYMLOOP_MAX",		CONSTANT,	_POSIX_SYMLOOP_MAX	},
  { "_POSIX_THREAD_DESTRUCTOR_ITERATIONS", CONSTANT,	_POSIX_THREAD_DESTRUCTOR_ITERATIONS	},
  { "_POSIX_THREAD_KEYS_MAX",		CONSTANT,	_POSIX_THREAD_KEYS_MAX	},
  { "_POSIX_THREAD_THREADS_MAX",	CONSTANT,	_POSIX_THREAD_THREADS_MAX	},
  { "_POSIX_TIMER_MAX",			CONSTANT,	_POSIX_TIMER_MAX	},
  { "_POSIX_TRACE_EVENT_NAME_MAX",	CONSTANT,	_POSIX_TRACE_EVENT_NAME_MAX	},
  { "_POSIX_TRACE_NAME_MAX",		CONSTANT,	_POSIX_TRACE_NAME_MAX	},
  { "_POSIX_TRACE_SYS_MAX",		CONSTANT,	_POSIX_TRACE_SYS_MAX	},
  { "_POSIX_TRACE_USER_EVENT_MAX",	CONSTANT,	_POSIX_TRACE_USER_EVENT_MAX	},
  { "_POSIX_TTY_NAME_MAX",		CONSTANT,	_POSIX_TTY_NAME_MAX	},
  { "_POSIX_TZNAME_MAX",		CONSTANT,	_POSIX_TZNAME_MAX	},
  { "_POSIX2_BC_BASE_MAX",		CONSTANT,	_POSIX2_BC_BASE_MAX	},
  { "_POSIX2_BC_DIM_MAX",		CONSTANT,	_POSIX2_BC_DIM_MAX	},
  { "_POSIX2_BC_SCALE_MAX",		CONSTANT,	_POSIX2_BC_SCALE_MAX	},
  { "_POSIX2_BC_STRING_MAX",		CONSTANT,	_POSIX2_BC_STRING_MAX	},
  { "_POSIX2_COLL_WEIGHTS_MAX",		CONSTANT,	_POSIX2_COLL_WEIGHTS_MAX	},
  { "_POSIX2_EXPR_NEXT_MAX",		CONSTANT,	_POSIX2_EXPR_NEST_MAX	},
  { "_POSIX2_LINE_MAX",			CONSTANT,	_POSIX2_LINE_MAX	},
  { "_POSIX2_RE_DUP_MAX",		CONSTANT,	_POSIX2_RE_DUP_MAX	},
  { "_XOPEN_IOV_MAX",			CONSTANT,	_XOPEN_IOV_MAX		},
  { "_XOPEN_NAME_MAX",			CONSTANT,	_XOPEN_NAME_MAX		},
  { "_XOPEN_PATH_MAX",			CONSTANT,	_XOPEN_PATH_MAX		},
  { "CHAR_BIT",				CONSTANT,	CHAR_BIT	},
  { "CHAR_MAX",				CONSTANT,	CHAR_MAX	},
  { "CHAR_MIN",				CONSTANT,	CHAR_MIN	},
  { "INT_MAX",				CONSTANT,	INT_MAX		},
  { "INT_MIN",				CONSTANT,	INT_MIN		},
  { "LLONG_MIN",			CONSTANT,	LLONG_MIN	},
  { "LLONG_MAX",			CONSTANT,	LLONG_MAX	},
  { "LONG_BIT",				CONSTANT,	LONG_BIT	},
  { "LONG_MAX",				CONSTANT,	LONG_MAX	},
  { "LONG_MIN",				CONSTANT,	LONG_MIN	},
  { "MB_LEN_MAX",			CONSTANT,	MB_LEN_MAX	},
  { "SCHAR_MAX",			CONSTANT,	SCHAR_MAX	},
  { "SCHAR_MIN",			CONSTANT,	SCHAR_MIN	},
  { "SHRT_MAX",				CONSTANT,	SHRT_MAX	},
  { "SHRT_MIN",				CONSTANT,	SHRT_MIN	},
  { "SSIZE_MAX",			CONSTANT,	SSIZE_MAX	},
  { "UCHAR_MAX",			CONSTANT,	UCHAR_MAX	},
  { "UINT_MAX",				CONSTANT,	UINT_MAX	},
  { "ULLONG_MAX",			CONSTANT,	ULLONG_MAX	},
  { "ULONG_MAX",			CONSTANT,	ULONG_MAX	},
  { "USHRT_MAX",			CONSTANT,	USHRT_MAX	},
  { "WORD_BIT",				CONSTANT,	WORD_BIT	},
  { "NL_ARGMAX",			CONSTANT,	NL_ARGMAX	},
  { "ML_LANGMAX",			CONSTANT,	NL_LANGMAX	},
  { "NL_MSGMAX",			CONSTANT,	NL_MSGMAX	},
  { "NL_SETMAX",			CONSTANT,	NL_SETMAX	},
  { "NL_TEXTMAX",			CONSTANT,	NL_TEXTMAX	},
  { "NZERO",				CONSTANT,	NZERO		},
  /* required by POSIX.1-2008 for compatibility with earlier versions */
  { "POSIX2_BC_BASE_MAX",		CONSTANT,	_POSIX2_BC_BASE_MAX	},
  { "POSIX2_BC_DIM_MAX",		CONSTANT,	_POSIX2_BC_DIM_MAX	},
  { "POSIX2_BC_SCALE_MAX",		CONSTANT,	_POSIX2_BC_SCALE_MAX	},
  { "POSIX2_BC_STRING_MAX",		CONSTANT,	_POSIX2_BC_STRING_MAX	},
  { "POSIX2_COLL_WEIGHTS_MAX",		CONSTANT,	_POSIX2_COLL_WEIGHTS_MAX	},
  { "POSIX2_EXPR_NEXT_MAX",		CONSTANT,	_POSIX2_EXPR_NEST_MAX	},
  { "POSIX2_LINE_MAX",			CONSTANT,	_POSIX2_LINE_MAX	},
  { "POSIX2_RE_DUP_MAX",		CONSTANT,	_POSIX2_RE_DUP_MAX	},

  /* Variables from fpathconf() */
  { "FILESIZEBITS",			PATHCONF,	_PC_FILESIZEBITS	},
  { "LINK_MAX",				PATHCONF,	_PC_LINK_MAX		},
  { "MAX_CANON",			PATHCONF,	_PC_MAX_CANON		},
  { "MAX_INPUT",			PATHCONF,	_PC_MAX_INPUT		},
  { "NAME_MAX",				PATHCONF,	_PC_NAME_MAX		},
  { "PATH_MAX",				PATHCONF,	_PC_PATH_MAX		},
  { "PIPE_BUF",				PATHCONF,	_PC_PIPE_BUF		},
  { "POSIX2_SYMLINKS",			PATHCONF,	_PC_2_SYMLINKS		},
  { "POSIX_ALLOC_SIZE_MIN",		PATHCONF,	_PC_ALLOC_SIZE_MIN	},
  { "POSIX_REC_INCR_XFER_SIZE",		PATHCONF,	_PC_REC_INCR_XFER_SIZE	},
  { "POSIX_REC_MAX_XFER_SIZE",		PATHCONF,	_PC_REC_MAX_XFER_SIZE	},
  { "POSIX_REC_MIN_XFER_SIZE",		PATHCONF,	_PC_REC_MIN_XFER_SIZE	},
  { "POSIX_REC_XFER_ALIGN",		PATHCONF,	_PC_REC_XFER_ALIGN	},
  { "SYMLINK_MAX",			PATHCONF,	_PC_SYMLINK_MAX		},
  { "_POSIX_CHOWN_RESTRICTED",		PATHCONF,	_PC_CHOWN_RESTRICTED	},
  { "_POSIX_NO_TRUNC",			PATHCONF,	_PC_NO_TRUNC		},
  { "_POSIX_VDISABLE",			PATHCONF,	_PC_VDISABLE		},
  { "_POSIX_ASYNC_IO",			PATHCONF,	_PC_ASYNC_IO		},
  { "_POSIX_PRIO_IO",			PATHCONF,	_PC_PRIO_IO		},
  { "_POSIX_SYNC_IO",			PATHCONF,	_PC_SYNC_IO		},
  { "_POSIX_TIMESTAMP_RESOLUTION",	PATHCONF,	_PC_TIMESTAMP_RESOLUTION	},

  /* Variables from sysconf() */
  { "AIO_LISTIO_MAX",			SYSCONF,	_SC_AIO_LISTIO_MAX	},
  { "AIO_MAX",				SYSCONF,	_SC_AIO_MAX		},
  { "AIO_PRIO_DELTA_MAX",		SYSCONF,	_SC_AIO_PRIO_DELTA_MAX	},
  { "ARG_MAX",				SYSCONF,	_SC_ARG_MAX		},
  { "ATEXIT_MAX",			SYSCONF,	_SC_ATEXIT_MAX		},
  { "BC_BASE_MAX",			SYSCONF,	_SC_BC_BASE_MAX		},
  { "BC_DIM_MAX",			SYSCONF,	_SC_BC_DIM_MAX		},
  { "BC_SCALE_MAX",			SYSCONF,	_SC_BC_SCALE_MAX	},
  { "BC_STRING_MAX",			SYSCONF,	_SC_BC_STRING_MAX	},
  { "CHILD_MAX",			SYSCONF,	_SC_CHILD_MAX		},
  { "CLK_TCK",				SYSCONF,	_SC_CLK_TCK		},
  { "COLL_WEIGHTS_MAX",			SYSCONF,	_SC_COLL_WEIGHTS_MAX	},
  { "DELAYTIMER_MAX",			SYSCONF,	_SC_DELAYTIMER_MAX	},
  { "EXPR_NEST_MAX",			SYSCONF,	_SC_EXPR_NEST_MAX	},
  { "GETGR_R_SIZE_MAX",			SYSCONF,	_SC_GETGR_R_SIZE_MAX	},
  { "GETPW_R_SIZE_MAX",			SYSCONF,	_SC_GETPW_R_SIZE_MAX	},
  { "HOST_NAME_MAX",			SYSCONF,	_SC_HOST_NAME_MAX	},
  { "IOV_MAX",				SYSCONF,	_SC_IOV_MAX		},
  { "LINE_MAX",				SYSCONF,	_SC_LINE_MAX		},
  { "LOGIN_NAME_MAX",			SYSCONF,	_SC_LOGIN_NAME_MAX 	},
  { "MQ_OPEN_MAX",			SYSCONF,	_SC_MQ_OPEN_MAX		},
  { "MQ_PRIO_MAX",			SYSCONF,	_SC_MQ_PRIO_MAX		},
  { "NGROUPS_MAX",			SYSCONF,	_SC_NGROUPS_MAX		},
  { "OPEN_MAX",				SYSCONF,	_SC_OPEN_MAX		},
  { "PAGE_SIZE",			SYSCONF,	_SC_PAGESIZE		},
  { "PAGESIZE",				SYSCONF,	_SC_PAGESIZE		},
  { "PTHREAD_DESTRUCTOR_ITERATIONS",	SYSCONF,	_SC_THREAD_DESTRUCTOR_ITERATIONS 	},
  { "PTHREAD_KEYS_MAX",			SYSCONF,	_SC_THREAD_KEYS_MAX 	},
  { "PTHREAD_STACK_MIN",		SYSCONF,	_SC_THREAD_STACK_MIN 	},
  { "PTHREAD_THREADS_MAX",		SYSCONF,	_SC_THREAD_THREADS_MAX 	},
  { "RE_DUP_MAX",			SYSCONF,	_SC_RE_DUP_MAX		},
  { "RTSIG_MAX",			SYSCONF,	_SC_RTSIG_MAX		},
  { "SEM_NSEMS_MAX",			SYSCONF,	_SC_SEM_NSEMS_MAX	},
  { "SEM_VALUE_MAX",			SYSCONF,	_SC_SEM_VALUE_MAX	},
  { "SIGQUEUE_MAX",			SYSCONF,	_SC_SIGQUEUE_MAX	},
  { "STREAM_MAX",			SYSCONF,	_SC_STREAM_MAX		},
  { "SYMLOOP_MAX",			SYSCONF,	_SC_SYMLOOP_MAX		},
  { "TIMER_MAX",			SYSCONF,	_SC_TIMER_MAX		},
  { "TTY_NAME_MAX",			SYSCONF,	_SC_TTY_NAME_MAX 	},
  { "TZNAME_MAX",			SYSCONF,	_SC_TZNAME_MAX		},
  { "_POSIX_ADVISORY_INFO",		SYSCONF,	_SC_ADVISORY_INFO	},
  { "_POSIX_ASYNCHRONOUS_IO",		SYSCONF,	_SC_ASYNCHRONOUS_IO 	},
  { "_POSIX_BARRIERS",			SYSCONF,	_SC_BARRIERS		},
  { "_POSIX_CLOCK_SELECTION",		SYSCONF,	_SC_CLOCK_SELECTION	},
  { "_POSIX_CPUTIME",			SYSCONF,	_SC_CPUTIME		},
  { "_POSIX_FSYNC",			SYSCONF,	_SC_FSYNC 		},
  { "_POSIX_IPV6",			SYSCONF,	_SC_IPV6		},
  { "_POSIX_JOB_CONTROL",		SYSCONF,	_SC_JOB_CONTROL		},
  { "_POSIX_MAPPED_FILES",		SYSCONF,	_SC_MAPPED_FILES 	},
  { "_POSIX_MEMLOCK",			SYSCONF,	_SC_MEMLOCK 		},
  { "_POSIX_MEMLOCK_RANGE",		SYSCONF,	_SC_MEMLOCK_RANGE 	},
  { "_POSIX_MEMORY_PROTECTION",		SYSCONF,	_SC_MEMORY_PROTECTION 	},
  { "_POSIX_MESSAGE_PASSING",		SYSCONF,	_SC_MESSAGE_PASSING 	},
  { "_POSIX_MONOTONIC_CLOCK",		SYSCONF,	_SC_MONOTONIC_CLOCK	},
  { "_POSIX_PRIORITIZED_IO",		SYSCONF,	_SC_PRIORITIZED_IO 	},
  { "_POSIX_PRIORITY_SCHEDULING",	SYSCONF,	_SC_PRIORITY_SCHEDULING 	},
  { "_POSIX_RAW_SOCKETS",		SYSCONF,	_SC_RAW_SOCKETS		},
  { "_POSIX_READER_WRITER_LOCKS",	SYSCONF,	_SC_READER_WRITER_LOCKS	},
  { "_POSIX_REALTIME_SIGNALS",		SYSCONF,	_SC_REALTIME_SIGNALS 	},
  { "_POSIX_REGEXP",			SYSCONF,	_SC_REGEXP		},
  { "_POSIX_SAVED_IDS",			SYSCONF,	_SC_SAVED_IDS		},
  { "_POSIX_SEMAPHORES",		SYSCONF,	_SC_SEMAPHORES 		},
  { "_POSIX_SHARED_MEMORY_OBJECTS",	SYSCONF,	_SC_SHARED_MEMORY_OBJECTS 	},
  { "_POSIX_SHELL",			SYSCONF,	_SC_SHELL		},
  { "_POSIX_SPAWN",			SYSCONF,	_SC_SPAWN		},
  { "_POSIX_SPIN_LOCKS",		SYSCONF,	_SC_SPIN_LOCKS		},
  { "_POSIX_SPORADIC_SERVER",		SYSCONF,	_SC_SPORADIC_SERVER	},
  { "_POSIX_SS_REPL_MAX",		SYSCONF,	_SC_SS_REPL_MAX		},
  { "_POSIX_SYNCHRONIZED_IO",		SYSCONF,	_SC_SYNCHRONIZED_IO 	},
  { "_POSIX_THREAD_ATTR_STACKADDR",	SYSCONF,	_SC_THREAD_ATTR_STACKADDR 	},
  { "_POSIX_THREAD_ATTR_STACKSIZE",	SYSCONF,	_SC_THREAD_ATTR_STACKSIZE 	},
  { "_POSIX_THREAD_CPUTIME",		SYSCONF,	_SC_THREAD_CPUTIME	},
  { "_POSIX_THREAD_PRIO_INHERIT",	SYSCONF,	_SC_THREAD_PRIO_INHERIT 	},
  { "_POSIX_THREAD_PRIO_PROTECT",	SYSCONF,	_SC_THREAD_PRIO_PROTECT 	},
  { "_POSIX_THREAD_PRIORITY_SCHEDULING",SYSCONF,	_SC_THREAD_PRIORITY_SCHEDULING 	},
  { "_POSIX_THREAD_PROCESS_SHARED",	SYSCONF,	_SC_THREAD_PROCESS_SHARED 	},
  { "_POSIX_THREAD_ROBUST_PRIO_INHERIT",SYSCONF,	_SC_THREAD_ROBUST_PRIO_INHERIT 	},
  { "_POSIX_THREAD_ROBUST_PRIO_PROTECT",SYSCONF,	_SC_THREAD_ROBUST_PRIO_PROTECT 	},
  { "_POSIX_THREAD_SAFE_FUNCTIONS",	SYSCONF,	_SC_THREAD_SAFE_FUNCTIONS	},
  { "_POSIX_THREAD_SPORADIC_SERVER",	SYSCONF,	_SC_THREAD_SPORADIC_SERVER	},
  { "_POSIX_THREADS",			SYSCONF,	_SC_THREADS 		},
  { "_POSIX_TIMEOUTS",			SYSCONF,	_SC_TIMEOUTS		},
  { "_POSIX_TIMERS",			SYSCONF,	_SC_TIMERS		},
  { "_POSIX_TRACE",			SYSCONF,	_SC_TRACE		},
  { "_POSIX_TRACE_EVENT_FILTER",	SYSCONF,	_SC_TRACE_EVENT_FILTER	},
  { "_POSIX_TRACE_EVENT_NAME_MAX",	SYSCONF,	_SC_TRACE_EVENT_NAME_MAX	},
  { "_POSIX_TRACE_INHERIT",		SYSCONF,	_SC_TRACE_INHERIT	},
  { "_POSIX_TRACE_LOG",			SYSCONF,	_SC_TRACE_LOG		},
  { "_POSIX_TRACE_NAME_MAX",		SYSCONF,	_SC_TRACE_NAME_MAX		},
  { "_POSIX_TRACE_SYS_MAX",		SYSCONF,	_SC_TRACE_SYS_MAX		},
  { "_POSIX_TRACE_USER_EVENT_MAX",	SYSCONF,	_SC_TRACE_USER_EVENT_MAX		},
  { "_POSIX_TYPED_MEMORY_OBJECTS",	SYSCONF,	_SC_TYPED_MEMORY_OBJECTS	},
  { "_POSIX_VERSION",			SYSCONF,	_SC_VERSION		},
  { "_POSIX_V7_ILP32_OFF32",		SYSCONF,	_SC_V7_ILP32_OFF32	},
  { "_POSIX_V7_ILP32_OFFBIG",		SYSCONF,	_SC_V7_ILP32_OFFBIG	},
  { "_POSIX_V7_LP64_OFF64",		SYSCONF,	_SC_V7_LP64_OFF64	},
  { "_POSIX_V7_LPBIG_OFFBIG",		SYSCONF,	_SC_V7_LPBIG_OFFBIG	},
  { "_POSIX_V6_ILP32_OFF32",		SYSCONF,	_SC_V6_ILP32_OFF32	},
  { "_POSIX_V6_ILP32_OFFBIG",		SYSCONF,	_SC_V6_ILP32_OFFBIG	},
  { "_POSIX_V6_LP64_OFF64",		SYSCONF,	_SC_V6_LP64_OFF64	},
  { "_POSIX_V6_LPBIG_OFFBIG",		SYSCONF,	_SC_V6_LPBIG_OFFBIG	},
  { "_POSIX2_C_BIND",			SYSCONF,	_SC_2_C_BIND		},
  { "_POSIX2_C_DEV",			SYSCONF,	_SC_2_C_DEV		},
  { "_POSIX2_CHAR_TERM",		SYSCONF,	_SC_2_CHAR_TERM		},
  { "_POSIX2_FORT_DEV",			SYSCONF,	_SC_2_FORT_DEV		},
  { "_POSIX2_FORT_RUN",			SYSCONF,	_SC_2_FORT_RUN		},
  { "_POSIX2_LOCALEDEF",		SYSCONF,	_SC_2_LOCALEDEF		},
  { "_POSIX2_PBS",			SYSCONF,	_SC_2_PBS		},
  { "_POSIX2_PBS_ACCOUNTING",		SYSCONF,	_SC_2_PBS_ACCOUNTING	},
  { "_POSIX2_PBS_CHECKPOINT",		SYSCONF,	_SC_2_PBS_CHECKPOINT	},
  { "_POSIX2_PBS_LOCATE",		SYSCONF,	_SC_2_PBS_LOCATE	},
  { "_POSIX2_PBS_MESSAGE",		SYSCONF,	_SC_2_PBS_MESSAGE	},
  { "_POSIX2_PBS_TRACK",		SYSCONF,	_SC_2_PBS_TRACK		},
  { "_POSIX2_SW_DEV",			SYSCONF,	_SC_2_SW_DEV		},
  { "_POSIX2_UPE",			SYSCONF,	_SC_2_UPE		},
  { "_POSIX2_VERSION",			SYSCONF,	_SC_2_VERSION		},
  { "_XBS5_ILP32_OFF32",		SYSCONF,	_SC_XBS5_ILP32_OFF32	},
  { "_XBS5_ILP32_OFFBIG",		SYSCONF,	_SC_XBS5_ILP32_OFFBIG	},
  { "_XBS5_LP64_OFF64",			SYSCONF,	_SC_XBS5_LP64_OFF64	},
  { "_XBS5_LPBIG_OFFBIG",		SYSCONF,	_SC_XBS5_LPBIG_OFFBIG	},
  { "_XOPEN_CRYPT",			SYSCONF,	_SC_XOPEN_CRYPT		},
  { "_XOPEN_ENH_I18N",			SYSCONF,	_SC_XOPEN_ENH_I18N	},
  { "_XOPEN_LEGACY",			SYSCONF,	_SC_XOPEN_LEGACY	},
  { "_XOPEN_REALTIME",			SYSCONF,	_SC_XOPEN_REALTIME	},
  { "_XOPEN_REALTIME_THREADS",		SYSCONF,	_SC_XOPEN_REALTIME_THREADS	},
  { "_XOPEN_SHM",			SYSCONF,	_SC_XOPEN_SHM		},
  { "_XOPEN_STREAMS",			SYSCONF,	_SC_XOPEN_STREAMS	},
  { "_XOPEN_UNIX",			SYSCONF,	_SC_XOPEN_UNIX		},
  { "_XOPEN_UUCP",			SYSCONF,	_SC_XOPEN_UUCP		},
  { "_XOPEN_VERSION",			SYSCONF,	_SC_XOPEN_VERSION	},
  /* for compatibility with earlier versions */
  { "POSIX2_C_BIND",			SYSCONF,	_SC_2_C_BIND		},
  { "POSIX2_C_DEV",			SYSCONF,	_SC_2_C_DEV		},
  { "POSIX2_CHAR_TERM",			SYSCONF,	_SC_2_CHAR_TERM		},
  { "POSIX2_FORT_DEV",			SYSCONF,	_SC_2_FORT_DEV		},
  { "POSIX2_FORT_RUN",			SYSCONF,	_SC_2_FORT_RUN		},
  { "POSIX2_LOCALEDEF",			SYSCONF,	_SC_2_LOCALEDEF		},
  { "POSIX2_PBS",			SYSCONF,	_SC_2_PBS		},
  { "POSIX2_PBS_ACCOUNTING",		SYSCONF,	_SC_2_PBS_ACCOUNTING	},
  { "POSIX2_PBS_CHECKPOINT",		SYSCONF,	_SC_2_PBS_CHECKPOINT	},
  { "POSIX2_PBS_LOCATE",		SYSCONF,	_SC_2_PBS_LOCATE	},
  { "POSIX2_PBS_MESSAGE",		SYSCONF,	_SC_2_PBS_MESSAGE	},
  { "POSIX2_PBS_TRACK",			SYSCONF,	_SC_2_PBS_TRACK		},
  { "POSIX2_SW_DEV",			SYSCONF,	_SC_2_SW_DEV		},
  { "POSIX2_UPE",			SYSCONF,	_SC_2_UPE		},
  { "POSIX2_VERSION",			SYSCONF,	_SC_2_VERSION		},
  /* implementation-specific values */
  { "_NPROCESSORS_CONF",		SYSCONF,	_SC_NPROCESSORS_CONF	},
  { "_NPROCESSORS_ONLN",		SYSCONF,	_SC_NPROCESSORS_ONLN	},
  { "_AVPHYS_PAGES",			SYSCONF,	_SC_AVPHYS_PAGES	},
  { "_PHYS_PAGES",			SYSCONF,	_SC_PHYS_PAGES		},

  { NULL, 				CONSTANT,	 0L }
};

struct spec_variable {
  const char *name;
  int valid;
};

static const struct spec_variable spec_table[] = {
  { "POSIX_V7_ILP32_OFF32",	0 },
  { "POSIX_V7_ILP32_OFFBIG",	1 },
  { "POSIX_V7_LP64_OFF64",	0 },
  { "POSIX_V7_LPBIG_OFFBIG",	0 },
  { "POSIX_V6_ILP32_OFF32",	0 },
  { "POSIX_V6_ILP32_OFFBIG",	1 },
  { "POSIX_V6_LP64_OFF64",	0 },
  { "POSIX_V6_LPBIG_OFFBIG",	0 },
  { "XBS5_ILP32_OFF32",		0 },
  { "XBS5_ILP32_OFFBIG",	1 },
  { "XBS5_LP64_OFF64",		0 },
  { "XBS5_LPBIG_OFFBIG",	0 },
  { NULL, 0 },
};

static int a_flag = 0;		/* list all variables */
static int v_flag = 0;		/* follow given specification */

static void
print_longvar (const char *name, long long value)
{
  if (a_flag)
    printf ("%-35s %lld\n", name, value);
  else
    printf ("%lld\n", value);
}

static void
print_strvar (const char *name, const char *sval)
{
  if (a_flag)
    printf ("%-35s %s\n", name, sval);
  else
    printf ("%s\n", sval);
}

static void
printvar (const struct conf_variable *cp, const char *pathname)
{
  size_t slen;
  char *sval;
  long val;

  switch (cp->type)
    {
    case CONSTANT:
      print_longvar (cp->name, cp->value);
      break;

    case CONFSTR:
      errno = 0;
      slen = confstr ((int) cp->value, NULL, 0);
      if (slen == 0)
	{
	  if (errno == 0)
	    print_strvar (cp->name, "undefined");
	  return;
	}

      if ((sval = malloc (slen)) == NULL)
	error (EXIT_FAILURE, 0, "Can't allocate %zu bytes", slen);

      errno = 0;
      if (confstr ((int) cp->value, sval, slen) == 0)
	print_strvar (cp->name, "undefined");
      else
	print_strvar (cp->name, sval);

      free (sval);
      break;

    case SYSCONF:
      errno = 0;
      if ((val = sysconf ((int) cp->value)) == -1)
	{
	  if (a_flag && errno != 0)
	    return; /* Just skip invalid variables */
	  print_strvar (cp->name, "undefined");
	}
      else
	print_longvar (cp->name, val);
      break;

    case PATHCONF:
      errno = 0;
      if ((val = pathconf (pathname, (int) cp->value)) == -1)
	{
	  if (a_flag && errno != 0)
	    return; /* Just skip invalid variables */
	  print_strvar (cp->name, "undefined");
	}
      else
	print_longvar (cp->name, val);
      break;
    }
}

static void
usage (int ret)
{
  fprintf (ret ? stderr : stdout,
"Usage: %1$s [-v specification] variable_name [pathname]\n"
"       %1$s -a [pathname]\n"
"\n"
"Get configuration values\n"
"\n"
"  -v specification     Indicate specific version for which configuration\n"
"                       values shall be fetched.\n"
"  -a, --all            Print all known configuration values\n"
"\n"
"Other options:\n"
"\n"
"  -h, --help           This text\n"
"  -V, --version        Print program version and exit\n"
"\n", program_invocation_short_name);
  exit (ret);
}

static void
print_version ()
{
  printf ("getconf (cygwin) %d.%d.%d\n"
	  "Get configuration values\n"
	  "Copyright (C) 2011 - %s Red Hat, Inc.\n"
	  "This is free software; see the source for copying conditions.  There is NO\n"
	  "warranty; not even for MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.\n",
	  CYGWIN_VERSION_DLL_MAJOR / 1000,
	  CYGWIN_VERSION_DLL_MAJOR % 1000,
	  CYGWIN_VERSION_DLL_MINOR,
	  strrchr (__DATE__, ' ') + 1);
}

struct option longopts[] = {
  {"all", no_argument, NULL, 'a'},
  {"help", no_argument, NULL, 'h'},
  {"version", no_argument, NULL, 'V'},
  {0, no_argument, NULL, 0}
};
const char *opts = "ahvV";

int
main (int argc, char **argv)
{
  int ch;
  const struct conf_variable *cp;
  const struct spec_variable *sp;
  const char *varname, *pathname;
  int found;

  setlocale (LC_ALL, "");

  while ((ch = getopt_long (argc, argv, opts, longopts, NULL)) != -1)
    {
      switch (ch)
	{
	case 'a':
	  a_flag = 1;
	  break;
	case 'v':
	  v_flag = 1;
	  break;
	case 'h':
	  usage (0);
	case 'V':
	  print_version ();
	  return 0;
	default:
	  fprintf (stderr, "Try `%s --help' for more information.\n",
		   program_invocation_short_name);
	  return 1;
	}
    }
  argc -= optind;
  argv += optind;

  if (v_flag)
    {
      if (a_flag || argc < 2)
	usage (1);
      for (sp = spec_table; sp->name != NULL; sp++)
	{
	  if (strcmp (argv[0], sp->name) == 0)
	    {
	      if (sp->valid != 1)
		error (EXIT_FAILURE, 0, "unsupported specification \"%s\"", argv[0]);
	      break;
	    }
	}
      if (sp->name == NULL)
	error (EXIT_FAILURE, 0, "unknown specification \"%s\"", argv[0]);
      argc--;
      argv++;
    }

  if (!a_flag)
    {
      if (argc == 0)
	usage (1);
      varname = argv[0];
      argc--;
      argv++;
    }
  else
    varname = NULL;

  if (argc > 1)
    usage (1);
  pathname = argv[0];  /* may be NULL */

  found = 0;
  for (cp = conf_table; cp->name != NULL; cp++)
    {
      if (a_flag || strcmp (varname, cp->name) == 0)
	{
	  /* LINTED weird expression */
	  if ((cp->type == PATHCONF) == (pathname != NULL))
	    {
	      printvar (cp, pathname);
	      found = 1;
	    }
	  else if (!a_flag)
	    usage (1);
	}
    }

  if (!a_flag && !found)
    error (EXIT_FAILURE, 0, "Unrecognized variable `%s'", varname);

  fflush (stdout);
  return ferror (stdout) ? EXIT_FAILURE : EXIT_SUCCESS;
}

