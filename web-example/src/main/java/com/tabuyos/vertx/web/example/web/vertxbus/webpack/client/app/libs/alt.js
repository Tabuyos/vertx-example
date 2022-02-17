import Alt from 'com/tabuyos/vertx/web/example/web/vertxbus/webpack/client/app/libs/alt';
// There's a chrome plugin for Alt that relies on this
//
import chromeDebug from 'alt/utils/chromeDebug';

const alt = new Alt();

chromeDebug(alt);

export default alt;
