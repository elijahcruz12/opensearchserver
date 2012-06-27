<?php
/*
 *  This file is part of OpenSearchServer.
 *
 *  Copyright (C) 2008-2011 Emmanuel Keller / Jaeksoft
 *
 *  http://www.open-search-server.com
 *
 *  OpenSearchServer is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  OpenSearchServer is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with OpenSearchServer.  If not, see <http://www.gnu.org/licenses/>.
 */

header('Content-type: text/html; charset=UTF-8');

define('BASE_DIR', dirname(__FILE__));
require BASE_DIR.'/../lib/oss_misc.lib.php';
require BASE_DIR.'/../lib/oss_api.class.php';
require BASE_DIR.'/../lib/oss_search.class.php';

$oss = new OSSAPI('http://localhost:8080/oss', 'emptyWebCrawler');

$oss->pattern(array(
	'http://www.google.com/*',
	'http://www.j3tel.fr',
	'http://apple-tv-hacks.com/*'
), true);
