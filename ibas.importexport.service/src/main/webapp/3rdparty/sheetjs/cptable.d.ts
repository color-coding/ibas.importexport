/* codepage.js (C) 2013-present SheetJS -- http://sheetjs.com */
// TypeScript Version: 2.2

/** Codepage index type (integer or string representation) */
type CP$Index = number | string;

/* Individual codepage converter */
interface CP$Conv {
	enc: { [n: string]: number; };
	dec: { [n: number]: string; };
}

/** Encode input type (string, array of characters, Buffer) */
type CP$String = string | string[] | Uint8Array;

/** Encode output / decode input type */
type CP$Data = string | number[] | Uint8Array;

/** General utilities */
interface CP$Utils {
	decode(cp: CP$Index, data: CP$Data): string;
	encode(cp: CP$Index, data: CP$String, opts?: any): CP$Data;
	hascp(n: number): boolean;
	magic: { [cp: string]: string };
}

/* note: TS cannot export top-level indexer, hence default workaround */
interface CP$Module {
	/** Version string */
	version: string;

	/** Utility Functions */
	utils: CP$Utils;

	/** Codepage Converters */
	[cp: number]: CP$Conv;
}
declare var cptable: CP$Module;
export = cptable;
export as namespace cptable;